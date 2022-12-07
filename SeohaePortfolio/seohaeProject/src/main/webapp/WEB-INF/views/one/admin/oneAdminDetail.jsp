<%    
/**
 * 1:1 문의 답변글 상세페이지(관리자)
 * @author seohae
 * @since 2017. 11. 11.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  1. (2017. 11. 11 / seohae / 최초생성)
 *
 * </pre>
 */
 %>
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/include-header.jsp" %>
<jsp:include page="/mainTop.do" />
<script>
$(document).ready(function(){
	/** 게시글 목록으로 돌아가기위해 [목록] 버튼 클릭시 */
	$("#btnList").click(function(){
		var obj = document.frm; //form태그의 name값=frm.
		obj.method="POST";
		obj.action="<c:url value='/oneAdminList.do' />"; //컨트롤러로 이동.
		obj.submit();
	});  
	
	/** 해당 문의글에 답변을 쓰기위해 [답변 글쓰기] 버튼 클릭시 */
	$("#btnReWrite").click(function(){ //수정버튼
		var obj = document.frm;
		obj.method = "POST";
		obj.action = "<c:url value='/oneAdminWrite.do' />";
		obj.submit();
	});  
});
</script>
<!-- History section -->
<section id="history" class="history sections">
    <div class="container">
        <div class="row">
            <div class="main_history">
                <div class="col-sm-6">
                    <div class="single_history_img">
                        <img src="assets/images/stab1.png" alt="" />
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="single_history_content">
                        <div class="head_title">
                            <h2>1:1 REQUEST</h2>
                        </div>
                        <p> 1:1 문의 게시판입니다. BINO를 이용하시는 회원님들의 모든 의견과 충고를 존중하겠습니다. 
                        	BINO에게 질문 또는 의견이 있으신 분들은 1:1 문의글을 작성해주세요. </p>

                        <a href="/oneList.do" class="btn btn-lg">1:1 문의게시판</a>
                    </div>
                </div>
            </div>
        </div><!--End of row -->
    </div><!--End of container -->
</section><!--End of history -->

<section id="contact" class="contact">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="contact_contant sections">
                    <div class="head_title text-center">
                        <h2>1:1 REQUEST</h2>
                        <div class="subtitle">
                            Nullam sit amet odio eu est aliquet euismod a a urna. Proin eu urna suscipit, dictum quam nec.  
                        </div>
                        <div class="separator"></div>
                    </div><!-- End off Head_title -->
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="single_contant_left padding-top-90 padding-bottom-90">
                               <!-- *********************************************************************************** -->
                             <form class="form-horizontal" name="frm">
					      		<input type="hidden" name="reqReNo" value="${OneVo.reqReNo}">
					      		<input type="hidden" name="userId" value="${OneVo.userId }" />
							<section class="panel">
								<header class="panel-heading">
									<div class="headerDivTitle"><span>1:1 문의내역</span></div>
								</header>
								<div class="panel-body">
									<div class="line line-dashed line-lg pull-in"></div>
									<div class="form-group">
										<label class="col-sm-1 control-label">내&nbsp;&nbsp;&nbsp;용</label>
										<div class="col-sm-11">
											<div style="height:240px">
												<div class="form-control freeTextarea" id="freeTextarea">${OneVo.reqReDesc } </div>
											</div>
										</div>
									</div>
									</div>
							</section>
						</form>
                            </div>
                        </div>
                        <div class="loginButton" style="text-align: center">
                         	<button type="button" class="btn btn-lg m_t_10" id="btnList">목록</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section><!-- End of contact section -->
<%@ include file="/WEB-INF/include/include-footer.jsp" %>