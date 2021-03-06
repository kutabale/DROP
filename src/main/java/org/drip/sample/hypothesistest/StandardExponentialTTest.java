
package org.drip.sample.hypothesistest;

import org.drip.measure.continuous.R1UnivariateExponential;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>StandardExponentialTTest</i> illustrates t-Test for a Standard Exponential Ensemble.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Bhattacharya, B., and D. Habtzghi (2002): Median of the p-value under the Alternate Hypothesis
 *  			American Statistician 56 (3) 202-206
 *  	</li>
 *  	<li>
 *  		Head, M. L., L. Holman, R, Lanfear, A. T. Kahn, and M. D. Jennions (2015): The Extent and
 *  			Consequences of p-Hacking in Science PLoS Biology 13 (3) e1002106
 *  	</li>
 *  	<li>
 *  		Wasserstein, R. L., and N. A. Lazar (2016): The ASA�s Statement on p-values: Context, Process,
 *  			and Purpose American Statistician 70 (2) 129-133
 *  	</li>
 *  	<li>
 *  		Wetzels, R., D. Matzke, M. D. Lee, J. N. Rouder, G, J, Iverson, and E. J. Wagenmakers (2011):
 *  		Statistical Evidence in Experimental Psychology: An Empirical Comparison using 855 t-Tests
 *  		Perspectives in Psychological Science 6 (3) 291-298
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
- *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation">Model Validation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/core">Core Model Validation Support Utilities</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StandardExponentialTTest
{

	private static final double UnivariateRandom()
		throws Exception
	{
		return R1UnivariateExponential.Standard().random();
	}

	private static final Sample GenerateSample (
		final int drawCount)
		throws Exception
	{
		double[] univariateRandomArray = new double[drawCount];

		for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
		{
			univariateRandomArray[drawIndex] = UnivariateRandom();
		}

		return new Sample (univariateRandomArray);
	}

	private static final Sample[] GenerateSampleArray (
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		Sample[] sampleArray = new Sample[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleArray[sampleIndex] = GenerateSample (drawCount);
		}

		return sampleArray;
	}

	private static final TestStatisticEvaluator[] MakeTestStatisticEvaluatorArray()
		throws Exception
	{
		return new TestStatisticEvaluator[]
		{
			new TestStatisticEvaluator()
			{
				public double evaluate (
					final double[] drawArray)
					throws Exception
				{
					double mean = 0.;
					int drawCount = drawArray.length;

					for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
					{
						mean += drawArray[drawIndex];
					}

					return mean / drawCount;
				}
			}
		};
	}

	private static final void TTest (
		final Ensemble ensemble,
		final int drawCount,
		final TestStatisticEvaluator testStatisticEvaluator)
		throws Exception
	{
		Sample testSample = GenerateSample (drawCount);

		org.drip.validation.hypothesis.TTestOutcome[] tTestArray = ensemble.tTest (
			testSample.applyTestStatistic (testStatisticEvaluator)
		);

		System.out.println (
			"\t| " + FormatUtil.FormatDouble (tTestArray[0].testStatistic(), 1, 4, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].sampleCount(), 3, 0, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleMean(), 1, 4, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleVariance(), 1, 8, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleStandardDeviation(), 1, 6, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleStandardError(), 1, 6, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleDegreesOfFreedom(), 2, 0, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensemblePredictiveConfidenceInterval(), 1, 6, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleTStatistics(), 1, 4, 1.) +
			" | " + FormatUtil.FormatDouble (tTestArray[0].ensembleStandardErrorOffset(), 1, 4, 1.) + " ||"
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 10000;
		int sampleCount = 100;
		int tTestCount = 25;

		TestStatisticEvaluator[] testStatisticEvaluatorArray = MakeTestStatisticEvaluatorArray();

		Ensemble ensemble = new Ensemble (
			GenerateSampleArray (
				drawCount,
				sampleCount
			),
			testStatisticEvaluatorArray
		);

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                       STANDARD UNIFORM t-TEST                                        ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                                                                           ||");

		System.out.println ("\t|            - Test Statistic                                                                          ||");

		System.out.println ("\t|            - Sample Count                                                                            ||");

		System.out.println ("\t|            - Ensemble Mean                                                                           ||");

		System.out.println ("\t|            - Ensemble Variance                                                                       ||");

		System.out.println ("\t|            - Ensemble Standard Deviation                                                             ||");

		System.out.println ("\t|            - Ensemble Standard Error                                                                 ||");

		System.out.println ("\t|            - Ensemble Degrees of Freedom                                                             ||");

		System.out.println ("\t|            - Ensemble Predictive Confidence Interval                                                 ||");

		System.out.println ("\t|            - Ensemble Test Statistic                                                                 ||");

		System.out.println ("\t|            - Ensemble Standard Error Offset                                                          ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		for (int tTestIndex = 0; tTestIndex < tTestCount; ++tTestIndex)
		{
			TTest (
				ensemble,
				drawCount,
				testStatisticEvaluatorArray[0]
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
