package net.kencochrane.raven;

import android.util.Base64;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: conferences
 * Date: 06/04/13
 * Time: 00:39

 */
public class AndroidClient extends Client {

    /**
     *
     */
    public static class AndroidMessage extends Client.Message {

        /**
         *
         * @param json
         * @param eventId
         * @param compress
         */
        public AndroidMessage(JSONObject json, String eventId, boolean compress) {
            super(json, eventId, compress);
        }

        /**
         *
         * @return
         */
        public String encoded() {
            byte[] raw = Utils.toUtf8(json.toJSONString());
            if (compress) {
                raw = Utils.compress(raw);
            }
            return new String(Base64.encode(raw, Base64.DEFAULT));
            //return encodeBase64String(raw);
        }
    }
    /**
     * Constructor that performs an autostart using the transport determined by the supplied dsn.
     * <p>
     * Watch out: this constructor will always use the supplied dsn and not look for a Sentry DSN in other locations
     * such as an environment variable or system property.
     * </p>
     *
     * @param dsn dsn to use
     */
    public AndroidClient(SentryDsn dsn) {
        super(dsn);
    }
    /**
     * Constructor using the transport determined by the supplied dsn.
     * <p>
     * Watch out: this constructor will always use the supplied dsn and not look for a Sentry DSN in other locations
     * such as an environment variable or system property.
     * </p>
     *
     * @param dsn       dsn to use
     * @param autoStart whether to start the underlying transport layer automatically
     */
    public AndroidClient(SentryDsn dsn, boolean autoStart) {
        super(dsn, autoStart);
    }

    /**
     *
     * @param message
     * @param timestamp
     * @param loggerClass
     * @param logLevel
     * @param culprit
     * @param tags
     * @return
     */
    public String captureMessage(String message, Long timestamp, String loggerClass, Integer logLevel, String culprit, Map<String, ?> tags) {
        timestamp = (timestamp == null ? Utils.now() : timestamp);
        Message msg = buildMessage(message, formatTimestamp(timestamp), loggerClass, logLevel, culprit, null, tags);
        AndroidMessage amsg = new AndroidMessage(msg.json, msg.eventId, msg.compress);
        send(amsg, timestamp);
        return msg.eventId;
    }

    /**
     *
     * @param message
     * @param timestamp
     */
    @Override
    protected void send(Message message, long timestamp) {
        super.send(message, timestamp);
    }

}
