package br.com.fiap.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="cResultado ")
public class CResultado{
	@Element(name="Servicos")
    private Servicos Servicos;

    public Servicos getServicos ()
    {
        return Servicos;
    }

    public void setServicos (Servicos Servicos)
    {
        this.Servicos = Servicos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Servicos = "+Servicos+"]";
    }


}
