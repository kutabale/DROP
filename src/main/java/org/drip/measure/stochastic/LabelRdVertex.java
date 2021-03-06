
package org.drip.measure.stochastic;

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
 * <i>LabelRdVertex</i> holds the Labeled R<sup>d</sup> Multi-Factor Latent State Vertex Realizations. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  			<b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic">Stochastics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LabelRdVertex extends org.drip.measure.stochastic.LabelBase
{
	private double[][] _vertexRd = null;

	/**
	 * LabelRdVertex Constructor
	 * 
	 * @param labelList The List of Labels
	 * @param vertexRd The R<sup>d</sup> Vertex Realizations
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LabelRdVertex (
		final java.util.List<java.lang.String> labelList,
		final double[][] vertexRd)
		throws java.lang.Exception
	{
		super (labelList);

		if (null == (_vertexRd = vertexRd))
		{
			throw new java.lang.Exception ("LabelRdVertex Constructor => Invalid Inputs");
		}

		int labelCount = labelList.size();

		if (null == _vertexRd[0] || labelCount != _vertexRd[0].length)
		{
			throw new java.lang.Exception ("LabelRdVertex Constructor => Invalid Inputs");
		}

		int vertexCount = _vertexRd.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (null == _vertexRd[vertexIndex] || labelCount != _vertexRd[vertexIndex].length ||
				!org.drip.numerical.common.NumberUtil.IsValid (_vertexRd[vertexIndex]))
			{
				throw new java.lang.Exception ("LabelRdVertex Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Vertex R<sup>d</sup> Values
	 * 
	 * @return The Vertex R<sup>d</sup> Values
	 */

	public double[][] vertexRd()
	{
		return _vertexRd;
	}

	/**
	 * Retrieve the Vertex R<sup>1</sup> Array for the Specified Label
	 * 
	 * @param label The Label
	 * 
	 * @return The Vertex R<sup>1</sup> Array
	 */

	public double[] vertexR1 (
		final java.lang.String label)
	{
		if (null == label || !_labelList.contains (label))
		{
			return null;
		}

		int vertexCount = _vertexRd.length;
		double[] vertexR1 = new double[vertexCount];

		int labelIndex = _labelIndexMap.get (label);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexR1[vertexIndex] = _vertexRd[vertexIndex][labelIndex];
		}

		return vertexR1;
	}
}
