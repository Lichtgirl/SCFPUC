package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("veiculoList")
public class VeiculoList extends EntityQuery<Veiculo> {

	private static final String EJBQL = "select veiculo from Veiculo veiculo";

	private static final String[] RESTRICTIONS = {
			"lower(veiculo.adquiridoDe) like lower(concat(#{veiculoList.veiculo.adquiridoDe},'%'))",
			"lower(veiculo.chassi) like lower(concat(#{veiculoList.veiculo.chassi},'%'))",
			"lower(veiculo.modalidadeAquisicao) like lower(concat(#{veiculoList.veiculo.modalidadeAquisicao},'%'))",
			"lower(veiculo.numPatrimonio) like lower(concat(#{veiculoList.veiculo.numPatrimonio},'%'))",
			"lower(veiculo.observacao) like lower(concat(#{veiculoList.veiculo.observacao},'%'))",
			"lower(veiculo.placaAnterior) like lower(concat(#{veiculoList.veiculo.placaAnterior},'%'))",
			"lower(veiculo.placaAtual) like lower(concat(#{veiculoList.veiculo.placaAtual},'%'))",};

	private Veiculo veiculo = new Veiculo();

	public VeiculoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}
}
