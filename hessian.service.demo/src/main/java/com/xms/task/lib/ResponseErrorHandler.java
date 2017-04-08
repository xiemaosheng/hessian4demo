package com.xms.task.lib;



import com.xms.task.code.Code;
import com.xms.task.config.Constants;
import com.xms.task.exception.ServiceInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

public class ResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        try {
            super.handleError(response);
        } catch (HttpStatusCodeException ex) {
            logger.error(Constants.MARKER_EXT, "http failed,  status:{},  body:{}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new ServiceInternalException(Code.SERVER_ERROR, ex);
        } catch (Exception ex) {
            logger.error(Constants.MARKER_EXT, "http failed", ex);
            throw new ServiceInternalException(Code.SERVER_ERROR, ex);
        }
    }
}
