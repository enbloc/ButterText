package buttertext.com.buttertext.model;

/**
 * Created by Gabriel on 1/8/2016.
 */
public class Message {

    int id;
    int type;
    String content;
    String sent_at;

    // Constructors
    public Message(){}

    public Message(int type, String content, String sent_at){
        this.type = type;
        this.content = content;
        this.sent_at = sent_at;
    }

    public Message(int id, int type, String content, String sent_at){
        this.id = id;
        this.type = type;
        this.content = content;
        this.sent_at = sent_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSent_at() {
        return sent_at;
    }

    public void setSent_at(String sent_at) {
        this.sent_at = sent_at;
    }
}
