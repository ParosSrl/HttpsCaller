import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.X509TrustManager;

public class Main {
    private static final HostnameVerifier NAIVE_HOSTNAME_VERIFIER = (hostname, session) -> true;
    private static final X509TrustManager NAIVE_TRUST_MANAGER = new NaiveTrustManager();

    private static final String CERTIFICATO_IN_BASE_64 = "";
    private static final String PASSWORD = "";

    private static final String URL = "";

    private static final Ssl ssl = new Ssl(CERTIFICATO_IN_BASE_64, PASSWORD, NAIVE_TRUST_MANAGER);

    public static void main(String[] args) {
        OkHttpClient httpsClient = new OkHttpClient.Builder()
                .hostnameVerifier(NAIVE_HOSTNAME_VERIFIER)
                .sslSocketFactory(ssl.getFactory(), NAIVE_TRUST_MANAGER)
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(MediaType.parse("application/json"), "ciao"))
                .build();
        httpsClient.newCall(request);
    }

}
