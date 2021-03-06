
package org.drip.simm.parameters;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>BucketVegaSettingsCR</i> holds the Vega Risk Weights, Concentration Thresholds, and Cross-Tenor
 * Correlations for each Credit Curve and its Tenor. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters">Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketVegaSettingsCR extends org.drip.simm.parameters.BucketSensitivitySettingsCR
{
	private double _vegaScaler = java.lang.Double.NaN;
	private double _historicalVolatilityRatio = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _tenorDeltaRiskWeight = null;

	/**
	 * Retrieve the ISDA 2.0 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_20 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_20 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRQSystemics20.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				org.drip.simm.credit.CRThresholdContainer20.QualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.0 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_20 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_20 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRNQSystemics20.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				org.drip.simm.credit.CRThresholdContainer20.NonQualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_21 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_21 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRQSystemics21.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				org.drip.simm.credit.CRThresholdContainer21.QualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_21 (
		final int bucketNumber)
	{
		org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			org.drip.simm.parameters.BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_21 (bucketNumber);

		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new BucketVegaSettingsCR (
				TenorRiskWeightMap (org.drip.simm.credit.CRNQSystemics21.VEGA_RISK_WEIGHT),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				org.drip.simm.credit.CRThresholdContainer21.NonQualifyingThreshold (bucketNumber).vega(),
				java.lang.Math.sqrt (365. / 14.) /
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketVegaSettingsCR Constructor
	 * 
	 * @param tenorVegaRiskWeight The Tenor Vega Risk Weight Map
	 * @param sameIssuerSeniorityCorrelation Same Issuer/Seniority Correlation
	 * @param differentIssuerSeniorityCorrelation Different Issuer/Seniority Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * @param vegaScaler The Vega Scaler
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * @param tenorDeltaRiskWeight The Credit Tenor Delta Risk Weight
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettingsCR (
		final java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight,
		final double sameIssuerSeniorityCorrelation,
		final double differentIssuerSeniorityCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final java.util.Map<java.lang.String, java.lang.Double> tenorDeltaRiskWeight)
		throws java.lang.Exception
	{
		super (
			tenorVegaRiskWeight,
			sameIssuerSeniorityCorrelation,
			differentIssuerSeniorityCorrelation,
			concentrationThreshold
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_vegaScaler = vegaScaler) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_historicalVolatilityRatio =
				historicalVolatilityRatio) ||
			null == (_tenorDeltaRiskWeight = tenorDeltaRiskWeight))
		{
			throw new java.lang.Exception ("BucketVegaSettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Vega Scaler
	 * 
	 * @return The Vega Scaler
	 */

	public double vegaScaler()
	{
		return _vegaScaler;
	}

	/**
	 * Retrieve the Historical Volatility Ratio
	 * 
	 * @return The Historical Volatility Ratio
	 */

	public double historicalVolatilityRatio()
	{
		return _historicalVolatilityRatio;
	}

	/**
	 * Retrieve the Tenor Delta Risk Weight
	 * 
	 * @return The Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorDeltaRiskWeight()
	{
		return _tenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the Tenor Vega Risk Weight
	 * 
	 * @return The Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight()
	{
		return super.tenorRiskWeight();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> tenorVegaRiskWeight = tenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> tenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorVegaRiskWeightEntry :
			tenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = tenorVegaRiskWeightEntry.getKey();

			if (!tenorVegaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			tenorRiskWeight.put (
				tenor,
				tenorVegaRiskWeightEntry.getValue() * _tenorDeltaRiskWeight.get (tenor) * _vegaScaler *
					_historicalVolatilityRatio
			);
		}

		return tenorRiskWeight;
	}
}
