package org.sopt.diary.domain.diary.api.dto;

import org.sopt.diary.domain.diary.DiaryBaseException;

public class DiaryContentLengthCursor {
    private final long id;
    private final int contentLength;

    public DiaryContentLengthCursor(long id, int contentLength) {
        this.id = id;
        this.contentLength = contentLength;
    }

    public long getId() {
        return id;
    }

    public int getContentLength() {
        return contentLength;
    }

    // {id}-{contentLength}
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder
                .append(id)
                .append("-")
                .append(contentLength)
                .toString();
    }

    public static DiaryContentLengthCursor fromString(String cursor) {
        String[] split = cursor.split("-");

        if (split.length != 2) {
            throw new InvalidCursorException();
        }

        final long id;
        final int contentLength;

        try {
            id = Long.parseLong(split[0]);
            contentLength = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new InvalidCursorException();
        }


        return new DiaryContentLengthCursor(id, contentLength);
    }

    public static class InvalidCursorException extends DiaryBaseException {

    }
}
