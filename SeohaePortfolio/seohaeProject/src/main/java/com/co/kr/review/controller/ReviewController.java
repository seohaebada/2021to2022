package com.co.kr.review.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.kr.bino.service.BinoService;
import com.co.kr.bino.vo.BinoVo;
import com.co.kr.book.vo.BookVo;
import com.co.kr.cart.service.CartService;
import com.co.kr.review.service.ReviewService;
import com.co.kr.review.vo.ReviewVo;
import com.co.kr.user.vo.UserVo;

/**
 * ReviewController
 * 
 * @author 김서해
 * @since 2017. 11. 07.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>

 * 1. (2017. 11. 07 / seohae / 최초생성)
 *
 * </pre>
 */

@Controller
public class ReviewController {
	
	private Log log = LogFactory.getLog(ReviewController.class);
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	private BinoService binoService;
	
	/**
	    * @Method ReviewList
	    * @Date 2017. 11. 07
	    * @Writter seohae
	    * @Param 
	    * @EditHistory
	    * @Discript 교재 리뷰 목록_로그인체크
	    * @Return String
	  */
	
    
    @RequestMapping(value="/reviewListCheck", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String ReviewList(@ModelAttribute ReviewVo reviewVo, Model model, HttpSession session){
    	
    	if (session.getAttribute("userId") == null){
    		return "fal";
    	}
    	return "ok";
    }
    
    /**
	    * @Method ReviewListGo
	    * @Date 2017. 11. 07
	    * @Writter seohae
	    * @Param 
	    * @EditHistory
	    * @Discript 교재 리뷰 목록
	    * @Return String
	  */
    @RequestMapping(value="/reviewList", method={RequestMethod.GET, RequestMethod.POST})
    public String ReviewListGo(@ModelAttribute ReviewVo reviewVo, Model model, HttpSession session, @RequestParam("pdNo") int pdNo){
    	//검색어에 맞는 게시물의 갯수 구하기
    	int totalCount = reviewService.getReviewCount(reviewVo);
    	
    	if(reviewVo.getPageCnt()==null)reviewVo.setPageSize(10);
    	else reviewVo.setPageSize(Integer.parseInt(reviewVo.getPageCnt()));
    	BookVo list = reviewService.bookDetail(reviewVo); 
    	reviewVo.setTotalCount(totalCount);
    	model.addAttribute("pageVO",reviewVo);
    	model.addAttribute("book",list);
    	model.addAttribute("reviewList",reviewService.review(reviewVo));
    	return "review/reviewList";
    }
    
    /**
	    * @Method reviewWrite
	    * @Date 2017. 11. 07
	    * @Writter seohae
	    * @Param 
	    * @EditHistory
	    * @Discript 교재 후기 글쓰기
	    * @Return String
	  */
	@RequestMapping(value="reviewWrite", method={RequestMethod.GET, RequestMethod.POST})
    public String reviewWrite(@ModelAttribute BookVo bookVo, Model model){
		model.addAttribute("book", bookVo);
    	return "review/reviewWrite";
    }
	
	/**
	    * @Method uploadFile
	    * @Date 2017. 11. 07
	    * @Writter seohae
	    * @Param 
	    * @EditHistory
	    * @Discript 교재 후기 파일업로드
	    * @Return String
	  */
	// 파일명 랜덤생성 메서드
    private String uploadFile(String originalName, byte[] fileData) throws Exception{
    	String path = "C:\\seohaeProject\\upload\\";
        // uuid 생성(Universal Unique IDentifier, 범용 고유 식별자)
        UUID uuid = UUID.randomUUID();
        // 랜덤생성+파일이름 저장
        String savedName = uuid.toString()+"_"+originalName;
        File target = new File(path, savedName);
        // 임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
        // FileCopyUtils.copy(바이트배열, 파일객체)
        FileCopyUtils.copy(fileData, target);
        return savedName;
    }
    
    /**
	    * @Method insertReview
	    * @Date 2017. 11. 07
	    * @Writter seohae
	    * @Param 
	    * @EditHistory
	    * @Discript 교재 후기 작성완료
	    * @Return String
	  */
    @RequestMapping(value="insertReivew")
    public String insertReview(@ModelAttribute ReviewVo reviewVo, HttpSession session, RedirectAttributes redirectAttributes)throws Exception{
    	System.out.println("@@@@@@@@@@@@@@"+reviewVo);
    	String userId = (String)session.getAttribute("userId");
        reviewVo.setWriter(userId);
    	if(!reviewVo.getImg().isEmpty()){
            String fileName = reviewVo.getImg().getOriginalFilename();
            fileName = uploadFile(fileName, reviewVo.getImg().getBytes());
            String path = "C:\\seohaeProject\\upload\\";
            try {
                new File(path).mkdirs(); // 디렉토리 생성
                // 임시디렉토리(서버)에 저장된 파일을 지정된 디렉토리로 전송
                reviewVo.getImg().transferTo(new File(path+fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            reviewVo.setUrl(fileName);
        }
    	reviewService.insertReview(reviewVo);
    	redirectAttributes.addAttribute("pdNo", reviewVo.getPdNo());
    	
    	//작성 후 10 bino 적립!
		BinoVo binoVo = new BinoVo();
		binoVo.setBinoCg("후기댓글 작성");
		binoVo.setBinoYn("Y");
		binoVo.setUserId(userId);
		binoVo.setBino(10);
		binoService.questionBino(binoVo);
		//총 적립 포인트 조회
		int userBinoAdd = binoService.userBinoAdd(userId)+10;
		
		//사용자 테이블의 bino 총액 변경
		UserVo userVo = new UserVo();
		userVo.setUserId(userId);
		userVo.setBino(userBinoAdd);
		binoService.userBinoUpdate(userVo);
		
    	return "redirect:/reviewList.do";
    }
}
