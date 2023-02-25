package app.service.other.impl;

import app.service.other.CustomTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Template implementation to build text content using parameter substitution.
 * Square brackets is used to designate parameters. For example template
 * "Hello, [username]!" has one parameter with name "username". Call of generate()
 * method with map containing one entry "username":"Dmitriy" returns generated
 * text "Hello, Dmitriy!"
 **/
public class CustomTemplateImpl implements CustomTemplate {
    private static class Node {
        Node(boolean isParameter, String value) {
            this.isParameter = isParameter;
            this.value = value;
        }

        final boolean isParameter;
        final String value;
    }

    private final List<Node> nodes = new ArrayList<>();

    /**
     * Constructs template object from source template text
     * @param source source template text
     **/
    public CustomTemplateImpl(String source) {
        StringBuilder builder = new StringBuilder();
        boolean parameterMode = false;

        for (char character : source.toCharArray()) {
            if (character == '[') {
                if (parameterMode) throw new RuntimeException("Invalid template");
                parameterMode = true;
                nodes.add(new Node(false, builder.toString()));
                builder = new StringBuilder();
            } else if (character == ']') {
                if (!parameterMode) throw new RuntimeException("Invalid template");
                parameterMode = false;
                nodes.add(new Node(true, builder.toString()));
                builder = new StringBuilder();
            } else {
                builder.append(character);
            }
        }

        if (parameterMode) {
            throw new RuntimeException("Invalid template");
        }

        if (builder.length() > 0) {
            nodes.add(new Node(false, builder.toString()));
        }
    }

    @Override
    public String generate(Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder();

        for (Node node : nodes) {
            if (node.isParameter) {
                if (!parameters.containsKey(node.value)) {
                    throw new RuntimeException("Required parameter \""+node.value+"\" does not exist");
                }

                builder.append(parameters.get(node.value));
            } else {
                builder.append(node.value);
            }
        }

        return builder.toString();
    }
}
