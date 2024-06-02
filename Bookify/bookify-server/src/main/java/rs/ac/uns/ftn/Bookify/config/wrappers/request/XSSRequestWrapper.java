package rs.ac.uns.ftn.Bookify.config.wrappers.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.owasp.encoder.Encode;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] sanitizedValues = new String[count];

        for (int i = 0; i < count; i++) {
            sanitizedValues[i] = sanitizeInput(values[i]);
        }

        return sanitizedValues;
    }

    @Override
    public String getHeader(String name) {
       String value = super.getHeader(name);
       return sanitizeInput(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return sanitizeInput(value);
    }

    private String sanitizeInput(String input) {
        return Encode.forHtml(input);
    }

}
