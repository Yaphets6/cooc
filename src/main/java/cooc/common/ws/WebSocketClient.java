package cooc.common.ws;


import cooc.conf.ServerBaseInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class WebSocketClient {
    private final CompletableFuture<WebSocket> ws;
    private final WebSocket webSocket;
    private final HttpClient client = HttpClient.newBuilder().build();
    private final Map<String,String> headers;
    private final WebSocket.Builder builder = client.newWebSocketBuilder();
    private  WebSocket.Listener listenerEvent ;
    private final ServerBaseInfo serverBaseInfo;

    public WebSocketClient(ServerBaseInfo serverBaseInfo,Map<String,String> headers) throws ExecutionException, InterruptedException {
        this.serverBaseInfo = serverBaseInfo;
        this.headers = headers;
        this.listenerEvent = new ListenerEvent();
        this.ws = init();
        this.webSocket = ws.get();
    }

    private CompletableFuture<WebSocket> init(){
        return builder.buildAsync(URI.create(serverBaseInfo.getBaseUrl()),listenerEvent);
    }

    private void setHeaders(){
        if(!headers.isEmpty()){
            headers.forEach(builder::header);
        }
    }


    public WebSocket getWebSocket() {
        return webSocket;
    }

    public static class ListenerEvent implements WebSocket.Listener {
        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("ws连接成功" + webSocket);

        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println("ws收到消息" + data.toString());
            webSocket.request(1);
            return null;
        }


        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            System.out.println("关闭ws连接" + reason);
            webSocket.request(1);
            return null;
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.out.println("ws连接报错:" + error);;
        }
    }

}
