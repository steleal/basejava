package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, UTF_8)) {
            JsonParser.write(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
