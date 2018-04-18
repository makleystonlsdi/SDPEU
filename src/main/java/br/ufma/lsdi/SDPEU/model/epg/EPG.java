package br.ufma.lsdi.SDPEU.model.epg;

import java.util.HashMap;

/**
 * Created by makleyston on 23/01/18.
 */

public class EPG {

    private Channel channel = new Channel();
    private Programme programme = new Programme();

    private String requester;

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }
}
