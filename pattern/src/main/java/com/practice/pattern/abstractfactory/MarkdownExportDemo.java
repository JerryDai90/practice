package com.practice.pattern.abstractfactory;


/**
 * markdown 转换pdf或者word.
 *
 * @author jerry
 * @date 2021 -11-20 17:43:27
 */
public class MarkdownExportDemo {


    public static void main(String[] args) {

        //wps
        MarkdownExportFactory factory = new WPSMarkdownExportFactory();
        factory.getHtmlDocument().toHtml("111");
        factory.getWordDocument().toWord("111");

        //microsoft
        factory = new MicrosoftMarkdownExportFactory();
        factory.getHtmlDocument().toHtml("111");
        factory.getWordDocument().toWord("111");
    }

}

////////定义外部需要使用的功能接口层
interface MarkdownExportFactory {
    HtmlDocument getHtmlDocument();

    WordDocument getWordDocument();
}

interface HtmlDocument {
    byte[] toHtml(String markdown);
}

interface WordDocument {
    byte[] toWord(String markdown);
}
////////


////////具体实现类


//////// WPS
class WPSHtml implements HtmlDocument {

    @Override
    public byte[] toHtml(String markdown) {
        System.out.println("WPS 转换html成功");
        return null;
    }
}

class WPSWord implements WordDocument {
    @Override
    public byte[] toWord(String markdown) {
        System.out.println("WPS 转换word成功");
        return null;
    }
}

class WPSMarkdownExportFactory implements MarkdownExportFactory {
    @Override
    public HtmlDocument getHtmlDocument() {
        return new WPSHtml();
    }

    @Override
    public WordDocument getWordDocument() {
        return new WPSWord();
    }
}


/////// 微软
class MicrosoftHtml implements HtmlDocument {

    @Override
    public byte[] toHtml(String markdown) {
        System.out.println("Microsoft 转换html成功");
        return null;
    }
}

class MicrosoftWord implements WordDocument {
    @Override
    public byte[] toWord(String markdown) {
        System.out.println("Microsoft 转换word成功");
        return null;
    }
}

class MicrosoftMarkdownExportFactory implements MarkdownExportFactory {
    @Override
    public HtmlDocument getHtmlDocument() {
        return new MicrosoftHtml();
    }

    @Override
    public WordDocument getWordDocument() {
        return new MicrosoftWord();
    }
}


