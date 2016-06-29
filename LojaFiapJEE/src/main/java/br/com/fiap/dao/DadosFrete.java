package br.com.fiap.dao;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="da")
public class DadosFrete {
    @Element(name = "name")
    String name;

    @Element(name = "price")
    String price;

    @Element(name = "description")
    String description;

    @Element(name = "calories")
    String calories;
}