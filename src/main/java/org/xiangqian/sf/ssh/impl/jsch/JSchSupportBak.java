//package org.xiangqian.sf.ssh.impl.jsch;
//
//import com.jcraft.jsch.*;
//import lombok.Getter;
//import org.apache.commons.io.IOUtils;
//import org.auto.deploy.util.Assert;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Function;
//
///**
// * JSch
// * http://www.jcraft.com/jsch/
// *
// * @author xiangqian
// * @date 13:50 2022/07/23
// */
//public abstract class JSchSupportBak implements Closeable {
//

//
//    public void connectChannel(Channel channel) throws JSchException {
//        channel.connect((int) channelConnectTimeout.toMillis());
//        Assert.isTrue(channel.isConnected(), "channel连接失败");
//    }
//
//    public static List<String> inputStreamToStrList(InputStream is, Function<String, String> function) throws IOException {
//        byte[] buffer = is.readAllBytes();
//        return byteArrayToStrList(buffer, 0, buffer.length, function);
//    }
//
//    /**
//     * @param buffer
//     * @param offset
//     * @param length
//     * @param function 命令行执行结果处理函数，Function<命令行处理结果, 自定义处理结果>
//     * @return
//     * @throws IOException
//     */
//    public static List<String> byteArrayToStrList(byte[] buffer, int offset, int length, Function<String, String> function) throws IOException {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer, offset, length), StandardCharsets.UTF_8));
//            List<String> results = new ArrayList<>();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                String result = Objects.nonNull(function) ? function.apply(line) : line;
//                if (Objects.isNull(result)) {
//                    break;
//                }
//                results.add(result);
//            }
//            return results;
//        } finally {
//            IOUtils.closeQuietly(reader);
//        }
//    }
//
//    public static void close(Channel channel) {
//        if (Objects.nonNull(channel)) {
//            channel.disconnect();
//        }
//    }
//
//}
