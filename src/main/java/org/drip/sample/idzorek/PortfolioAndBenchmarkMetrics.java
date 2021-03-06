
package org.drip.sample.idzorek;

import org.drip.measure.bayesian.*;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput;
import org.drip.portfolioconstruction.asset.*;
import org.drip.portfolioconstruction.bayesian.*;
import org.drip.service.env.EnvManager;

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
 * <i>PortfolioAndBenchmarkMetrics</i> demonstrates the Prior-Posterior Portfolio Statistics using the
 * Black-Litterman Model augmented with the Idzorek Model. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios,
 *  			Goldman Sachs Asset Management
 *  	</li>
 *  	<li>
 *  		Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User
 *  			Specified Confidence Levels, Ibbotson Associates, Chicago
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/idzorek/README.md">Idzorek (2005) User Confidence Setting</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioAndBenchmarkMetrics {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblTau = 0.025;
		double dblRiskAversion = 3.07;
		double dblRiskFreeRate = 0.00;

		String[] astrAssetID = new String[] {
			"US BONDS                       ",
			"INTERNATIONAL BONDS            ",
			"US LARGE GROWTH                ",
			"US LARGE VALUE                 ",
			"US SMALL GROWTH                ",
			"US SMALL VALUE                 ",
			"INTERNATIONAL DEVELOPED EQUITY ",
			"INTERNATIONAL EMERGING EQUITY  "
		};

		double[] adblAssetEquilibriumWeight = new double[] {
			0.1934,
			0.2613,
			0.1209,
			0.1209,
			0.0134,
			0.0134,
			0.2418,
			0.0349
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
			{ 0.001005,  0.001328, -0.000579, -0.000675,  0.000121,  0.000128, -0.000445, -0.000437},
			{ 0.001328,  0.007277, -0.001307, -0.000610, -0.002237, -0.000989,  0.001442, -0.001535},
			{-0.000579, -0.001307,  0.059582,  0.027588,  0.063497,  0.023036,  0.032967,  0.048039},
			{-0.000675, -0.000610,  0.027588,  0.029609,  0.026572,  0.021465,  0.020697,  0.029854},
			{ 0.000121, -0.002237,  0.063497,  0.026572,  0.102488,  0.042744,  0.039943,  0.065994},
			{ 0.000128, -0.000989,  0.023036,  0.021465,  0.042744,  0.032056,  0.019881,  0.032235},
			{-0.000445,  0.001442,  0.032967,  0.020697,  0.039943,  0.019881,  0.028355,  0.035064},
			{-0.000437, -0.001535,  0.048039,  0.029854,  0.065994,  0.032235,  0.035064,  0.079958}
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00,  0.00},
			{ -1.00,  1.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00,  0.90, -0.90,  0.10, -0.10,  0.00,  0.00}
		};

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.0525,
			0.0025,
			0.0200
		};

		double[][] aadblProjectionExcessReturnsCovariance = ProjectionDistributionLoading.ProjectionCovariance (
			aadblAssetExcessReturnsCovariance,
			aadblAssetSpaceViewProjection,
			dblTau
		);

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			new MultivariateMeta (
				new String[] {
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3"
				}
			),
			adblProjectionExpectedExcessReturns,
			aadblProjectionExcessReturnsCovariance
		);

		BlackLittermanCombinationEngine blce = new BlackLittermanCombinationEngine (
			ForwardReverseOptimizationOutput.Reverse (
				Portfolio.Standard (
					astrAssetID,
					adblAssetEquilibriumWeight
				),
				aadblAssetExcessReturnsCovariance,
				dblRiskAversion
			),
			new PriorControlSpecification (
				false,
				dblRiskFreeRate,
				dblTau
			),
			new ProjectionSpecification (
				viewDistribution,
				aadblAssetSpaceViewProjection
			)
		);

		JointPosteriorMetrics jpm = blce.customConfidenceRun().combinationMetrics();

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jpm.joint();

		double[] adblAssetSpaceJointReturns = jointDistribution.mean();

		ForwardReverseOptimizationOutput fromPrior = ForwardReverseOptimizationOutput.Reverse (
			Portfolio.Standard (
				astrAssetID,
				adblAssetEquilibriumWeight
			),
			aadblAssetExcessReturnsCovariance,
			dblRiskAversion
		);

		PortfolioMetrics pmPrior = fromPrior.optimalMetrics();

		ForwardReverseOptimizationOutput fromPosterior = ForwardReverseOptimizationOutput.Forward (
			astrAssetID,
			adblAssetSpaceJointReturns,
			aadblAssetExcessReturnsCovariance,
			dblRiskAversion
		);

		PortfolioMetrics pmPosterior = fromPosterior.optimalMetrics();

		PortfolioBenchmarkMetrics pbm = fromPosterior.benchmarkMetrics (pmPrior);

		System.out.println ("\n\t|---------------------------------------------------------||");

		System.out.println ("\t| EXCESS RETURN MEAN               => " +
			FormatUtil.FormatDouble (pmPrior.excessReturnsMean(), 1, 3, 100.) + "%  | " +
			FormatUtil.FormatDouble (pmPosterior.excessReturnsMean(), 1, 3, 100.) + "%  ||"
		);

		System.out.println ("\t| EXCESS RETURN VARIANCE           => " +
			FormatUtil.FormatDouble (pmPrior.excessReturnsVariance(), 1, 5, 1.) + " | " +
			FormatUtil.FormatDouble (pmPosterior.excessReturnsVariance(), 1, 5, 1.) + " ||"
		);

		System.out.println ("\t| EXCESS RETURN STANDARD DEVIATION => " +
			FormatUtil.FormatDouble (pmPrior.excessReturnsStandardDeviation(), 1, 3, 100.) + "%  |" +
			FormatUtil.FormatDouble (pmPosterior.excessReturnsStandardDeviation(), 2, 3, 100.) + "%  ||"
		);

		System.out.println ("\t| BETA                             => " +
			FormatUtil.FormatDouble (fromPrior.benchmarkMetrics (pmPrior).beta(), 1, 5, 1.) + " | " +
			FormatUtil.FormatDouble (pbm.beta(), 1, 5, 1.) + " ||"
		);

		System.out.println ("\t| ACTIVE BETA                      => " +
			FormatUtil.FormatDouble (0., 1, 5, 1.) + " | " +
			FormatUtil.FormatDouble (pbm.activeBeta(), 1, 5, 1.) + " ||"
		);

		System.out.println ("\t| RESIDUAL RETURN                  => " +
			FormatUtil.FormatDouble (0., 1, 3, 100.) + "%  | " +
			FormatUtil.FormatDouble (pbm.residualReturn(), 1, 3, 100.) + "%  ||"
		);

		System.out.println ("\t| RESIDUAL RISK                    => " +
			FormatUtil.FormatDouble (0., 1, 3, 100.) + "%  | " +
			FormatUtil.FormatDouble (pbm.residualRisk(), 1, 3, 100.) + "%  ||"
		);

		System.out.println ("\t| ACTIVE RETURN                    => " +
			FormatUtil.FormatDouble (0., 1, 3, 100.) + "%  | " +
			FormatUtil.FormatDouble (pbm.activeReturn(), 1, 3, 100.) + "%  ||"
		);

		System.out.println ("\t| ACTIVE RISK                      => " +
			FormatUtil.FormatDouble (0., 1, 3, 100.) + "%  | " +
			FormatUtil.FormatDouble (pbm.activeRisk(), 1, 3, 100.) + "%  ||"
		);

		System.out.println ("\t| SHARPE RATIO                     => " +
			FormatUtil.FormatDouble (pmPrior.sharpeRatio(), 1, 5, 1.) + " | " +
			FormatUtil.FormatDouble (pmPosterior.sharpeRatio(), 1, 5, 1.) + " ||"
		);

		System.out.println ("\t|---------------------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
