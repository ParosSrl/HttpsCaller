import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Base64;

public final class Ssl {
    private final String base64;
    private final String password;
    private final X509TrustManager trustManager;

    public Ssl(String base64, String password, X509TrustManager trustManager) {
        this.base64 = base64;
        this.password = password;
        this.trustManager = trustManager;
    }

    public SSLSocketFactory getFactory() {
        try (final InputStream certificateStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64))) {
            SSLContext context = SSLContext.getInstance("TLS");
            final KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(certificateStream, password.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keystore, password.toCharArray());

            context.init(kmf.getKeyManagers(), new TrustManager[] { trustManager }, null);

            return context.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
