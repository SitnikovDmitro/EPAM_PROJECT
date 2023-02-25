package app.tags;

import app.service.other.Localizator;
import app.service.other.impl.LocalizatorImpl;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Jsp localization tag support class
 **/
public class LocalizationTagSupport extends TagSupport {
    private final Localizator localizator = LocalizatorImpl.getInstance();
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() {
        try {
            String lang = (String)pageContext.getSession().getAttribute("lang");
            JspWriter out = pageContext.getOut();
            out.print(localizator.localize(lang, key));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}