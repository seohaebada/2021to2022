package com.seohae.batch.batch.fileBatch3.job;

import com.seohae.batch.batch.fileBatch1.entity.Customer;
import com.seohae.batch.batch.fileBatch2.entity.Transaction;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.util.ArrayList;

/**
 * @author Michael Minella
 */
public class CustomerFileReader implements ResourceAwareItemReaderItemStream<Customer> {

    private Object curItem = null;

    private ResourceAwareItemReaderItemStream<Object> delegate;

    public CustomerFileReader(ResourceAwareItemReaderItemStream<Object> delegate) {
        this.delegate = delegate;
    }

    /**
     * Customer 아이템을 하나 읽어들이고 조합하는 역할
     * @return
     * @throws Exception
     */
    public Customer read() throws Exception {
        /* 파일에서 고객 레코드를 읽어들인다 */
        if(curItem == null) {
            curItem = delegate.read();
        }

        Customer item = (Customer) curItem;
        curItem = null;

		if(item != null) {
			item.setTransactions(new ArrayList<>());

            /**
             * peek() : 현재 처리중인 Customer 를 처리하는 과정에서 레코드를 미리 읽어논다.
             */
			while(peek() instanceof Transaction) {
				item.getTransactions().add((Transaction) curItem);
				curItem = null;
			}
		}

        return item;
    }

    private Object peek() throws Exception {
        if (curItem == null) {
            curItem = delegate.read();
        }
        return curItem;
    }

    public void close() throws ItemStreamException {
        delegate.close();
    }

    public void open(ExecutionContext arg0) throws ItemStreamException {
        delegate.open(arg0);
    }

    public void update(ExecutionContext arg0) throws ItemStreamException {
        delegate.update(arg0);
    }

    @Override
    public void setResource(Resource resource) {
        this.delegate.setResource(resource);
    }
}
