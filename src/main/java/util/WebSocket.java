package util;

import db.MongoDBFactoryConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        run();
    }

    private static void run() throws IOException, NoSuchAlgorithmException {
        try (ServerSocket server = new ServerSocket(80)) {
            runSocketServer(server);
        }
    }

    private static void runSocketServer(ServerSocket server) throws IOException, NoSuchAlgorithmException {
        LOGGER.info("Server has started on 127.0.0.1:80.\r\nWaiting for a connection…");
        Socket client = server.accept();

        while (true) {
            // can test this with
            // $ curl -X GET "127.0.0.1:80"
            LOGGER.info("A client connected.");
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            try (Scanner s = new Scanner(in, "UTF-8")) {
                try {
                    processResponse(s, out);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
            client = server.accept();
        }
    }

    private static void processResponse(Scanner s, OutputStream out) throws NoSuchAlgorithmException, IOException {
        String data = s.useDelimiter("\\r\\n\\r\\n").next();
        Matcher get = Pattern.compile("^GET").matcher(data);
        if (get.find()) {
            Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
            match.find();
            byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                    + "Connection: Upgrade\r\n"
                    + "Upgrade: websocket\r\n"
                    + "Sec-WebSocket-Accept: "
                    + Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")))
                    + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
            out.write(response, 0, response.length);
            byte[] decoded = new byte[6];
            byte[] encoded = new byte[]{(byte) 198, (byte) 131, (byte) 130, (byte) 182, (byte) 194, (byte) 135};
            byte[] key = new byte[]{(byte) 167, (byte) 225, (byte) 225, (byte) 210};
            for (int i = 0; i < encoded.length; i++) {
                decoded[i] = (byte) (encoded[i] ^ key[i & 0x3]);
            }
            LOGGER.info("decoded={}", decoded);
        }
    }
}
