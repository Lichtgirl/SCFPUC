package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("gastosList")
public class GastosList extends EntityQuery<Gastos> {

	private static final String EJBQL = "select gastos from Gastos gastos";

	private static final String[] RESTRICTIONS = {"lower(gastos.veiculo.placaAtual) like lower(concat(#{gastosList.gastos.veiculo.placaAtual},'%'))",};

	private Gastos gastos = new Gastos();

	public GastosList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Gastos getGastos() {
		return gastos;
	}
}
