
package org.drip.execution.hjb;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>NonDimensionalCostEvolverSystemic</i> implements the 1D HJB-based Single Step Optimal Trajectory Cost
 * Step Evolver using the Systemic Coordinated Variation Version of the Stochastic Volatility and the
 * Transaction Function arising from the Realization of the Market State Variable as described in the
 * "Trading Time" Model. The References are:
 * 
 * <br><br>
 *  <ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 			of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/README.md">Hamilton Jacobin Bellman Based Optimal Evolution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonDimensionalCostEvolverSystemic extends org.drip.execution.hjb.NonDimensionalCostEvolver
{

	/**
	 * Construct a Standard NonDimensionalCostEvolverSystemic Instance
	 * 
	 * @param ou The Underlying Ornstein-Unlenbeck Reference Process
	 * 
	 * @return The Standard NonDimensionalCostEvolverSystemic Instance
	 */

	public static final NonDimensionalCostEvolverSystemic Standard (
		final org.drip.measure.process.OrnsteinUhlenbeck ou)
	{
		try {
			return new NonDimensionalCostEvolverSystemic (ou,
				org.drip.execution.hjb.NonDimensionalCostEvolver.SINGULAR_URGENCY_THRESHOLD, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override protected double advance (
		final org.drip.execution.hjb.NonDimensionalCost ndc,
		final org.drip.execution.latent.MarketState ms,
		final double[] adblMarketStateTweak,
		final double dblNonDimensionalRiskAversion)
		throws java.lang.Exception
	{
		double dblNonDimensionalCost = ndc.realization();

		double dblMarketState = ms.liquidity() + adblMarketStateTweak[0];

		double dblBurstiness = ornsteinUnlenbeckProcess().referenceBurstiness();

		org.drip.execution.hjb.NonDimensionalCostSystemic ndcs =
			(org.drip.execution.hjb.NonDimensionalCostSystemic) ndc;

		return java.lang.Math.exp (-dblMarketState) * (dblNonDimensionalRiskAversion *
			dblNonDimensionalRiskAversion - dblNonDimensionalCost * dblNonDimensionalCost) + 0.5 *
				dblBurstiness * dblBurstiness * ndcs.jacobian() - dblMarketState * ndcs.gradient();
	}

	/**
	 * NonDimensionalCostEvolverSystemic Constructor
	 * 
	 * @param ou The Underlying Ornstein-Unlenbeck Reference Process
	 * @param bAsymptoticEnhancedEulerCorrection Asymptotic Enhanced Euler Correction Application Flag
	 * @param dblAsymptoticEulerUrgencyThreshold The Asymptotic Euler Urgency Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NonDimensionalCostEvolverSystemic (
		final org.drip.measure.process.OrnsteinUhlenbeck ou,
		final double dblAsymptoticEulerUrgencyThreshold,
		final boolean bAsymptoticEnhancedEulerCorrection)
		throws java.lang.Exception
	{
		super (ou, dblAsymptoticEulerUrgencyThreshold, bAsymptoticEnhancedEulerCorrection);
	}

	@Override public org.drip.execution.hjb.NonDimensionalCost evolve (
		final org.drip.execution.hjb.NonDimensionalCost ndc,
		final org.drip.execution.latent.MarketState ms,
		final double dblNonDimensionalRiskAversion,
		final double dblNonDimensionalTime,
		final double dblNonDimensionalTimeIncrement)
	{
		if (null == ndc || !(ndc instanceof org.drip.execution.hjb.NonDimensionalCostSystemic) || null
			== ms || !org.drip.numerical.common.NumberUtil.IsValid (dblNonDimensionalRiskAversion) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblNonDimensionalTime) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblNonDimensionalTimeIncrement))
			return null;

		double dblMarketState = ms.liquidity();

		double dblMarketStateIncrement = 0.01 * dblMarketState;

		double dblMarketStateExponentiation = java.lang.Math.exp (dblMarketState);

		if (asymptoticEulerUrgencyThreshold() * dblNonDimensionalTime < 1.) {
			if (!asymptoticEnhancedEulerCorrection())
				return org.drip.execution.hjb.NonDimensionalCostSystemic.LinearThreshold
					(dblMarketStateExponentiation, dblNonDimensionalTime);

			double dblBurstiness = ornsteinUnlenbeckProcess().referenceBurstiness();

			double dblNonDimensionalCostCross = -0.5 * dblMarketState * dblMarketStateExponentiation;

			return org.drip.execution.hjb.NonDimensionalCostSystemic.EulerEnhancedLinearThreshold
				(dblMarketState, ((1. / dblNonDimensionalTimeIncrement) + 0.25 * dblBurstiness *
					dblBurstiness) * java.lang.Math.exp (dblMarketState) + dblNonDimensionalCostCross,
						dblNonDimensionalCostCross);
		}

		try {
			double dblCostIncrementMid = advance (ndc, ms, new double[] {0.}, dblNonDimensionalRiskAversion)
				* dblNonDimensionalTimeIncrement;

			double dblCostIncrementUp = advance (ndc, ms, new double[] {dblMarketStateIncrement},
				dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblCostIncrementDown = advance (ndc, ms, new double[] {-1. * dblMarketStateIncrement},
				dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblCost = ndc.realization() + dblCostIncrementMid;

			return new org.drip.execution.hjb.NonDimensionalCostSystemic (dblCost, 0.5 *
				(dblCostIncrementUp - dblCostIncrementDown) / dblMarketStateIncrement, (dblCostIncrementUp +
					dblCostIncrementDown - 2. * dblCostIncrementMid) / (dblMarketStateIncrement *
						dblMarketStateIncrement), dblCost / dblMarketStateExponentiation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
