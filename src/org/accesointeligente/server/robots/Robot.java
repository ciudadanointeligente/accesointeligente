package org.accesointeligente.server.robots;

import org.accesointeligente.model.Institution;
import org.accesointeligente.model.Request;
import org.accesointeligente.server.robots.sgs.*;
import org.accesointeligente.shared.RequestStatus;

public abstract class Robot {
	protected String username;
	protected String password;

	public abstract void login() throws RobotException;

	public abstract Request makeRequest(Request request) throws RobotException;

	public abstract RequestStatus checkRequestStatus(Request request) throws RobotException;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Robot getRobot(Institution institution) {
		switch (institution.getInstitutionClass()) {
			case AgenciaDeCooperacionInternacional:
				return new AgenciaDeCooperacionInternacional();
			case ArmadaDeChile:
				return new ArmadaDeChile();
			case CajaDePrevisionDeLaDefensaNacional:
				return new CajaDePrevisionDeLaDefensaNacional();
			case ComisionAdministradoraDelSistemaDeCreditosParaEstudiosSuperiores:
				return new ComisionAdministradoraDelSistemaDeCreditosParaEstudiosSuperiores();
			case ComisionChilenaDeEnergiaNuclear:
				return new ComisionChilenaDeEnergiaNuclear();
			case ComisionChilenaDelCobre:
				return new ComisionChilenaDelCobre();
			case ComisionNacionalDeEnergia:
				return new ComisionNacionalDeEnergia();
			case ComisionNacionalDeInvestigacionEnCienciaYTecnologia:
				return new ComisionNacionalDeInvestigacionEnCienciaYTecnologia();
			case ComisionNacionalDeRiego:
				return new ComisionNacionalDeRiego();
			case ComiteDeInversionesExtranjeras:
				return new ComiteDeInversionesExtranjeras();
			case ConsejoDeDefensaDelEstado:
				return new ConsejoDeDefensaDelEstado();
			case ConsejoDeRectoresDeLasUniversidadesChilenas:
				return new ConsejoDeRectoresDeLasUniversidadesChilenas();
			case ConsejoNacionalDeAcreditacion:
				return new ConsejoNacionalDeAcreditacion();
			case ConsejoNacionalDeLaCulturaYLasArtes:
				return new ConsejoNacionalDeLaCulturaYLasArtes();
			case ConsejoNacionalDeTelevision:
				return new ConsejoNacionalDeTelevision();
			case CorporacionDeAsistenciaJudicialRegionBiobio:
				return new CorporacionDeAsistenciaJudicialRegionBiobio();
			case CorporacionDeAsistenciaJudicialRegionesTarapacaYAntofagasta:
				return new CorporacionDeAsistenciaJudicialRegionesTarapacaYAntofagasta();
			case CorporacionDeAsistenciaJudicialRegionMetropolitana:
				return new CorporacionDeAsistenciaJudicialRegionMetropolitana();
			case CorporacionDeAsistenciaJudicialRegionValparaiso:
				return new CorporacionDeAsistenciaJudicialRegionValparaiso();
			case CorporacionNacionalDeDesarrolloIndigena:
				return new CorporacionNacionalDeDesarrolloIndigena();
			case CorporacionNacionalForestal:
				return new CorporacionNacionalForestal();
			case DefensaCivilDeChile:
				return new DefensaCivilDeChile();
			case DefensoriaPenalPublica:
				return new DefensoriaPenalPublica();
			case DireccionAdministrativa:
				return new DireccionAdministrativa();
			case DireccionDeComprasYContratacionPublica:
				return new DireccionDeComprasYContratacionPublica();
			case DireccionDelTrabajo:
				return new DireccionDelTrabajo();
			case DireccionDePresupuestos:
				return new DireccionDePresupuestos();
			case DireccionDePrevisionDeCarabinerosDeChile:
				return new DireccionDePrevisionDeCarabinerosDeChile();
			case DireccionGeneralDeAeronauticaCivil:
				return new DireccionGeneralDeAeronauticaCivil();
			case DireccionGeneralDeCreditoPrendario:
				return new DireccionGeneralDeCreditoPrendario();
			case DireccionGeneralDelTerritorioMaritimoYMarinaMercante:
				return new DireccionGeneralDelTerritorioMaritimoYMarinaMercante();
			case DireccionGeneralDeMovilizacionNacional:
				return new DireccionGeneralDeMovilizacionNacional();
			case DireccionGeneralDeRelacionesEconomicasInternacionales:
				return new DireccionGeneralDeRelacionesEconomicasInternacionales();
			case DireccionNacionalDeFronterasYLimitesDelEstado:
				return new DireccionNacionalDeFronterasYLimitesDelEstado();
			case DireccionNacionalDelServicioCivil:
				return new DireccionNacionalDelServicioCivil();
			case EjercitoDeChile:
				return new EjercitoDeChile();
			case FiscaliaNacionalEconomica:
				return new FiscaliaNacionalEconomica();
			case FondoDeSolidaridadEInversionSocial:
				return new FondoDeSolidaridadEInversionSocial();
			case FondoNacionalDeSalud:
				return new FondoNacionalDeSalud();
			case FuerzaAereaDeChile:
				return new FuerzaAereaDeChile();
			case GendarmeriaDeChile:
				return new GendarmeriaDeChile();
			case GobiernoRegionalDeAntofagasta:
				return new GobiernoRegionalDeAntofagasta();
			case GobiernoRegionalDeAricaYParinacota:
				return new GobiernoRegionalDeAricaYParinacota();
			case GobiernoRegionalDeAtacama:
				return new GobiernoRegionalDeAtacama();
			case GobiernoRegionalDeAysen:
				return new GobiernoRegionalDeAysen();
			case GobiernoRegionalDeCoquimbo:
				return new GobiernoRegionalDeCoquimbo();
			case GobiernoRegionalDeLaAraucania:
				return new GobiernoRegionalDeLaAraucania();
			case GobiernoRegionalDelBiobio:
				return new GobiernoRegionalDelBiobio();
			case GobiernoRegionalDelMaule:
				return new GobiernoRegionalDelMaule();
			case GobiernoRegionalDeLosLagos:
				return new GobiernoRegionalDeLosLagos();
			case GobiernoRegionalDeLosRios:
				return new GobiernoRegionalDeLosRios();
			case GobiernoRegionalDeMagallanesYAntarticaChilena:
				return new GobiernoRegionalDeMagallanesYAntarticaChilena();
			case GobiernoRegionalDeOHiggins:
				return new GobiernoRegionalDeOHiggins();
			case GobiernoRegionalDeTarapaca:
				return new GobiernoRegionalDeTarapaca();
			case GobiernoRegionalDeValparaiso:
				return new GobiernoRegionalDeValparaiso();
			case InstitutoAntarticoChileno:
				return new InstitutoAntarticoChileno();
			case InstitutoDePrevisionSocial:
				return new InstitutoDePrevisionSocial();
			case InstitutoDeSeguridadLaboral:
				return new InstitutoDeSeguridadLaboral();
			case InstitutoGeograficoMilitar:
				return new InstitutoGeograficoMilitar();
			case InstitutoNacionalDeLaJuventud:
				return new InstitutoNacionalDeLaJuventud();
			case InstitutoNacionalDelDeporte:
				return new InstitutoNacionalDelDeporte();
			case InstitutoNacionalDePropiedadIntelectual:
				return new InstitutoNacionalDePropiedadIntelectual();
			case JuntaDeAeronauticaCivil:
				return new JuntaDeAeronauticaCivil();
			case JuntaNacionalDeAuxilioEscolarYBecas:
				return new JuntaNacionalDeAuxilioEscolarYBecas();
			case JuntaNacionalDeJardinesInfantiles:
				return new JuntaNacionalDeJardinesInfantiles();
			case OficinaNacionalDeEmergencia:
				return new OficinaNacionalDeEmergencia();
			case OficinasDeEstudiosYPoliticasAgrarias:
				return new OficinasDeEstudiosYPoliticasAgrarias();
			case PoliciaDeInvestigaciones:
				return new PoliciaDeInvestigaciones();
			case PresidenciaDeLaRepublica:
				return new PresidenciaDeLaRepublica();
			case ServicioAerofotogrametricoFach:
				return new ServicioAerofotogrametricoFach();
			case ServicioAgricolaYGanadero:
				return new ServicioAgricolaYGanadero();
			case ServicioDeSaludArauco:
				return new ServicioDeSaludArauco();
			case ServicioDeSaludAtacama:
				return new ServicioDeSaludAtacama();
			case ServicioDeSaludAysen:
				return new ServicioDeSaludAysen();
			case ServicioDeSaludCoquimbo:
				return new ServicioDeSaludCoquimbo();
			case ServicioDeSaludDeChiloe:
				return new ServicioDeSaludDeChiloe();
			case ServicioDeSaludDelReloncavi:
				return new ServicioDeSaludDelReloncavi();
			case ServicioDeSaludMagallanes:
				return new ServicioDeSaludMagallanes();
			case ServicioDeSaludOhiggins:
				return new ServicioDeSaludOhiggins();
			case ServicioDeSaludOsorno:
				return new ServicioDeSaludOsorno();
			case ServicioHidrograficoYOceanograficoDeLaArmada:
				return new ServicioHidrograficoYOceanograficoDeLaArmada();
			case ServicioNacionalDeAduanas:
				return new ServicioNacionalDeAduanas();
			case ServicioNacionalDeGeologiaYMineria:
				return new ServicioNacionalDeGeologiaYMineria();
			case ServicioNacionalDelAdultoMayor:
				return new ServicioNacionalDelAdultoMayor();
			case ServicioNacionalDeLaMujer:
				return new ServicioNacionalDeLaMujer();
			case ServicioNacionalDelConsumidor:
				return new ServicioNacionalDelConsumidor();
			case ServicioNacionalDeMenores:
				return new ServicioNacionalDeMenores();
			case ServicioNacionalDePesca:
				return new ServicioNacionalDePesca();
			case ServicioNacionalDeTurismo:
				return new ServicioNacionalDeTurismo();
			case SubsecretariaDeAgricultura:
				return new SubsecretariaDeAgricultura();
			case SubsecretariaDeAviacion:
				return new SubsecretariaDeAviacion();
			case SubsecretariaDeBienesNacionales:
				return new SubsecretariaDeBienesNacionales();
			case SubsecretariaDeCarabineros:
				return new SubsecretariaDeCarabineros();
			case SubsecretariaDeDesarrolloRegional:
				return new SubsecretariaDeDesarrolloRegional();
			case SubsecretariaDeEconomia:
				return new SubsecretariaDeEconomia();
			case SubsecretariaDeGuerra:
				return new SubsecretariaDeGuerra();
			case SubsecretariaDeInvestigaciones:
				return new SubsecretariaDeInvestigaciones();
			case SubsecretariaDeJusticia:
				return new SubsecretariaDeJusticia();
			case SubsecretariaDelTrabajo:
				return new SubsecretariaDelTrabajo();
			case SubsecretariaDeMarina:
				return new SubsecretariaDeMarina();
			case SubsecretariaDeMineria:
				return new SubsecretariaDeMineria();
			case SubsecretariaDePesca:
				return new SubsecretariaDePesca();
			case SubsecretariaDePlanificacionYCooperacion:
				return new SubsecretariaDePlanificacionYCooperacion();
			case SubsecretariaDePrevisionSocial:
				return new SubsecretariaDePrevisionSocial();
			case SubsecretariaDeRelacionesExteriores:
				return new SubsecretariaDeRelacionesExteriores();
			case SubsecretariaDeTelecomunicaciones:
				return new SubsecretariaDeTelecomunicaciones();
			case SubsecretariaDeTransporte:
				return new SubsecretariaDeTransporte();
			case SuperintendenciaDeBancosEInstitucionesFinancieras:
				return new SuperintendenciaDeBancosEInstitucionesFinancieras();
			case SuperintendenciaDeCasinosDeJuego:
				return new SuperintendenciaDeCasinosDeJuego();
			case SuperintendenciaDeElectricidadYCombustibles:
				return new SuperintendenciaDeElectricidadYCombustibles();
			case SuperintendenciaDePensiones:
				return new SuperintendenciaDePensiones();
			case SuperintendenciaDeQuiebras:
				return new SuperintendenciaDeQuiebras();
			default:
				return null;
		}
	}
}
