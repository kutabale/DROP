
package org.drip.portfolioconstruction.optimizer;

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
 * <i>RebalancerAnalytics</i> holds the Analytics from a given Rebalancing Run.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer">Optimizer</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RebalancerAnalytics
{
	private double _dblObjectiveValue = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.composite.Holdings _holdingsFinal = null;
	private org.drip.portfolioconstruction.asset.PortfolioMetrics _portfolioMetrics = null;
	private org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics _portfolioBenchmarkMetrics = null;
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapObjectiveTermRealization
		= null;
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.optimizer.ConstraintRealization>
			_mapConstraintRealization = null;

	/**
	 * RebalancerAnalytics Constructor
	 * 
	 * @param dblObjectiveValue The Objective Value
	 * @param holdingsFinal The Final Holdings
	 * @param mapObjectiveTermRealization Map of the Realized Objective Terms
	 * @param mapConstraintRealization Map of the Constraint Terms
	 * @param portfolioMetrics Portfolio Metrics
	 * @param portfolioBenchmarkMetrics Portfolio Benchmark Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RebalancerAnalytics (
		final double dblObjectiveValue,
		final org.drip.portfolioconstruction.composite.Holdings holdingsFinal,
		final org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>
			mapObjectiveTermRealization,
		final
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.optimizer.ConstraintRealization>
				mapConstraintRealization,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics portfolioMetrics,
		final org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics portfolioBenchmarkMetrics)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblObjectiveValue = dblObjectiveValue) ||
			null == (_holdingsFinal = holdingsFinal) ||
			null == (_mapObjectiveTermRealization = mapObjectiveTermRealization) ||
			null == (_mapConstraintRealization = mapConstraintRealization))
			throw new java.lang.Exception ("RebalancerAnalytics Constructor => Invalid Inputs!");

		_portfolioMetrics = portfolioMetrics;
		_portfolioBenchmarkMetrics = portfolioBenchmarkMetrics;
	}

	/**
	 * Retrieve the Objective Term
	 * 
	 * @return Objective Term
	 */

	public double objectiveValue()
	{
		return _dblObjectiveValue;
	}

	/**
	 * Retrieve the Portfolio Metrics
	 * 
	 * @return The Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioMetrics portfolioMetrics()
	{
		return _portfolioMetrics;
	}

	/**
	 * Retrieve the Portfolio Benchmark Metrics
	 * 
	 * @return The Portfolio Benchmark Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics portfolioBenchmarkMetrics()
	{
		return _portfolioBenchmarkMetrics;
	}

	/**
	 * Retrieve the Final Holdings of the Optimizer Run
	 * 
	 * @return Final Holdings of the Optimizer Run
	 */

	public org.drip.portfolioconstruction.composite.Holdings finalHoldings()
	{
		return _holdingsFinal;
	}

	/**
	 * Retrieve the Map of Constraint Realizations
	 * 
	 * @return Map of Constraint Realizations
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.optimizer.ConstraintRealization>
		constraintRealizaton()
	{
		return _mapConstraintRealization;
	}

	/**
	 * Retrieve the Map of Objective Term Realizations
	 * 
	 * @return Map of Objective Term Realizations
	 */

	public java.util.Map<java.lang.String, java.lang.Double> objectiveTermRealizaton()
	{
		return _mapObjectiveTermRealization;
	}
}
