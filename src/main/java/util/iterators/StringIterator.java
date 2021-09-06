package util.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class StringIterator implements Iterator<String> {
    private String data;
    private int length;
    private int index = 0;

    public StringIterator(String data) {
        this.data = data;
        this.length = data.length();
    }

    @Override
    public boolean hasNext() {
        return index < length;
    }

    @Override
    public String next() {
        if (hasNext()) {
            return String.valueOf(data.charAt(index++));
        }
        throw new NoSuchElementException();
    }

    public String peek() {
        if (hasNext()) {
            return String.valueOf(data.charAt(index));
        }
        throw new NoSuchElementException();
    }

    public boolean isPeek(String value) {
        if (hasNext()) {
            return String.valueOf(data.charAt(index)).equals(value);
        }
        throw new NoSuchElementException();
    }


    public String eatWhile(Predicate<String> predicate) {
        StringBuilder builder = new StringBuilder();

        while (hasNext() && predicate.test(peek()))
            builder.append(next());

        return builder.toString();
    }

    public String eatWhitespace() {
        return eatWhile(String::isBlank);
    }

    public String eatWord() {
        return eatWhile(ch -> !ch.isBlank());
    }

}
