
package org.drip.portfolioconstruction.constraint;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>LimitRiskTermVariance</i> holds the Details of a Variance Based Limit Risk Constraint Term.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint">Constraint</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitRiskTermVariance extends org.drip.portfolioconstruction.constraint.LimitRiskTerm
{
	private double[] _adblBenchmarkHoldings = null;

	/**
	 * LimitRiskTermVariance Constructor
	 * 
	 * @param strName Name of the LimitRiskTermVariance Constraint
	 * @param scope Scope of the LimitRiskTermVariance Constraint
	 * @param unit Unit of the LimitRiskTermVariance Constraint
	 * @param dblMinimum Minimum Limit Value of the LimitRiskTermVariance Constraint
	 * @param dblMaximum Maximum Limit Value of the LimitRiskTermVariance Constraint
	 * @param aadblAssetCovariance Asset Co-variance
	 * @param adblBenchmarkHoldings Array of the Benchmark Holdings
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public LimitRiskTermVariance (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[][] aadblAssetCovariance,
		final double[] adblBenchmarkHoldings)
		throws java.lang.Exception
	{
		super (
			strName,
			"CT_LIMIT_TOTAL_RISK",
			"Limits the Variance Based Total Risk",
			scope,
			unit,
			dblMinimum,
			dblMaximum,
			aadblAssetCovariance
		);

		int iNumBenchmarkHoldings = null == (_adblBenchmarkHoldings = adblBenchmarkHoldings) ? 0 :
			_adblBenchmarkHoldings.length;

		if (0 != iNumBenchmarkHoldings && (aadblAssetCovariance[0].length != iNumBenchmarkHoldings ||
			!org.drip.numerical.common.NumberUtil.IsValid (_adblBenchmarkHoldings)))
			throw new java.lang.Exception ("LimitRiskTermVariance Constructor => Invalid Benchmark");
	}

	/**
	 * Retrieve the Constricted Benchmark Holdings
	 * 
	 * @return The Constricted Benchmark Holdings
	 */

	public double[] benchmarkHoldings()
	{
		return _adblBenchmarkHoldings;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return assetCovariance().length;
			}

			@Override public double evaluate (
				final double[] adblFinalHoldings)
				throws java.lang.Exception
			{
				double[][] aadblAssetCovariance = assetCovariance();

				int iNumAsset = aadblAssetCovariance.length;
				double dblVariance = 0;

				if (null == adblFinalHoldings || !org.drip.numerical.common.NumberUtil.IsValid
					(adblFinalHoldings) || adblFinalHoldings.length != iNumAsset)
					throw new java.lang.Exception
						("LimitRiskTermVariance::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i)
				{
					double dblHoldingsOffsetI = adblFinalHoldings[i];

					if (null != _adblBenchmarkHoldings) dblHoldingsOffsetI -= _adblBenchmarkHoldings[i];

					for (int j = 0; j < iNumAsset; ++j)
					{
						double dblHoldingsOffsetJ = adblFinalHoldings[j];

						if (null != _adblBenchmarkHoldings) dblHoldingsOffsetJ -= _adblBenchmarkHoldings[j];

						dblVariance += dblHoldingsOffsetI * aadblAssetCovariance[i][j] * dblHoldingsOffsetJ;
					}
				}

				return dblVariance;
			}
		};
	}
}
