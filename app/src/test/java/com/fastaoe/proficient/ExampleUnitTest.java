package com.fastaoe.proficient;

import org.junit.Test;

import static android.R.id.list;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testOrigin(){
        WordDoc originDoc = new WordDoc();
        originDoc.setText("原型文档");
        originDoc.setImage("图片1");
        originDoc.setImage("图片2");
        originDoc.showDoc();

        WordDoc doc = originDoc.clone();
        doc.showDoc();
        doc.setText("修改之后的文档");
        doc.setImage("修改图片3");
        doc.showDoc();

        originDoc.showDoc();
    }
}