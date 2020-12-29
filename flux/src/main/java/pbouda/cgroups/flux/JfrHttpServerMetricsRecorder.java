package pbouda.cgroups.flux;

import jdk.jfr.*;
import reactor.netty.http.server.HttpServerMetricsRecorder;

import java.net.SocketAddress;
import java.time.Duration;

public class JfrHttpServerMetricsRecorder implements HttpServerMetricsRecorder {

    private final HttpExchangeEvent exchangeEvent = new HttpExchangeEvent();

    @Override
    public void recordDataReceivedTime(String uri, String method, Duration time) {
        exchangeEvent.begin();
        exchangeEvent.dataReceivedTime = time.toNanos();
    }

    @Override
    public void recordDataReceived(SocketAddress remoteAddress, String uri, long bytes) {
        exchangeEvent.address = remoteAddress.toString();
        exchangeEvent.dataReceived = bytes;
    }

    @Override
    public void recordDataSent(SocketAddress remoteAddress, String uri, long bytes) {
        exchangeEvent.dataSent = bytes;
    }

    @Override
    public void recordResponseTime(String uri, String method, String status, Duration time) {
        exchangeEvent.responseTime = time.toNanos();
    }

    @Override
    public void recordDataSentTime(String uri, String method, String status, Duration time) {
        exchangeEvent.dataSentTime = time.toNanos();

        exchangeEvent.uri = uri;
        exchangeEvent.method = method;
        exchangeEvent.status = status;
        exchangeEvent.end();
        exchangeEvent.commit();
    }

    @Override
    public void incrementErrorsCount(SocketAddress remoteAddress, String uri) {
        // noop
    }

    @Override
    public void recordTlsHandshakeTime(SocketAddress remoteAddress, Duration time, String status) {
        // noop
    }

    @Override
    public void recordDataReceived(SocketAddress remoteAddress, long bytes) {
        // noop
    }

    @Override
    public void recordDataSent(SocketAddress remoteAddress, long bytes) {
        // noop
    }

    @Override
    public void incrementErrorsCount(SocketAddress remoteAddress) {
        // noop
    }

    @Override
    public void recordConnectTime(SocketAddress remoteAddress, Duration time, String status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void recordResolveAddressTime(SocketAddress remoteAddress, Duration time, String status) {
        throw new UnsupportedOperationException();
    }

    @Label("Request/Response Exchange")
    @Description("Information about a single HTTP Request/Response Exchange")
    @Category({"HTTP", "Exchange"})
    @Name("http.Exchange")
//    @Registered(false)
//    @Enabled(false)
    @StackTrace(false)
    static class HttpExchangeEvent extends Event {

        @Label("HTTP Uri")
        String uri;

        @Label("HTTP Method")
        String method;

        @Label("HTTP Response Status")
        String status;

        @Label("Remote Address")
        String address;

        @Label("Data Received Time")
        @Description("Time spent on reading an entire Request")
        @Timespan
        long dataReceivedTime;

        @Label("Data Sent Time")
        @Description("Time spent on writing an entire Response")
        @Timespan
        long dataSentTime;

        @Label("Data Received")
        @Description("Total amount of data received from a socket")
        @DataAmount
        long dataReceived;

        @Label("Response Time")
        @Description("Total time spent on processing of Request/Response")
        @Timespan
        long responseTime;

        @Label("Data Sent")
        @Description("Total amount of data sent to a socket")
        @DataAmount
        long dataSent;
    }
}
