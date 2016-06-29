package br.com.fiap.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Servicos")
public class Servicos
{
	@Element(name="cServico")
    private CServico cServico;

    public CServico getCServico ()
    {
        return cServico;
    }

    public void setCServico (CServico cServico)
    {
        this.cServico = cServico;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cServico = "+cServico+"]";
    }
}