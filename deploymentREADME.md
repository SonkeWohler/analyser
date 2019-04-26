---


---

<h1 id="derivative-analyser-deployment-readme">Derivative Analyser Deployment README</h1>
<p>Sonke Wohler, BSc (Hons) Computing Science and Physics at the University of Aberdeen, 51552212.</p>
<p>This guide is meant to introduce and guide you through my Honours Project demonstration software.</p>
<h2 id="the-demonstration-software">The Demonstration Software</h2>
<p>The attached software contains the Busines Logic that attempts to analyse data, as well as a simple GUI and a data generator, in order to demonstrate this analysis and the concepts it makes use of.</p>
<h3 id="running-the-demonstration">Running the Demonstration</h3>
<p>The demonstration software is completely contained in the <code>run.jar</code> that should come with this guide and requires <a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">Java 11</a> <sup class="footnote-ref"><a href="#fn1" id="fnref1">1</a></sup> . With Java installed and registered in your System Path you can type <code>java -jar run.jar</code> from commanline. This should work similarly on Linux. If you have any issues with Java, here are some guides that should cover your operating system:</p>
<ul>
<li><a href="https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-61460339-5500-40CC-9006-D4FC3FBCFC0D">Windows Platforms</a><sup class="footnote-ref"><a href="#fn2" id="fnref2">2</a></sup></li>
<li><a href="https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8">Linux Platforms</a><sup class="footnote-ref"><a href="#fn3" id="fnref3">3</a></sup></li>
<li><a href="https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE">MacOS</a><sup class="footnote-ref"><a href="#fn4" id="fnref4">4</a></sup></li>
</ul>
<h3 id="what-the-software-can-do">What the Software Can Do</h3>
<p>In the software you will see three sections. On top there are four text fields to define the data set, below you can select properties the generated data will have, and below that you will see the generated data and the analysis.</p>
<h4 id="define-a-data-set">Define a Data Set</h4>
<p>The data will be generated starting at <code>x=base</code>, the first parameter you can define. Data points will be generated a distance of <code>step</code> appart, with a total number of <code>length</code>.  Finally, the <code>precision</code> parameter tells the analyser roughly how noisy the data can be expected to be. <strong>This does not influence the noise that your data is generated with</strong>, rather, it influences what noise is expected when analysisng the generated data. Low precision (i.e. a high value like 50) means that very strong noise will be ignored, but it may impact on the analyser’s ability to correctly classify functions. High precision (i.e. a low value like 10<sup>-10</sup>) makes analysis very exact, but makes it extremely sensitive to noise in the data.</p>
<h4 id="generating-functions">Generating Functions</h4>
<p>Below the text fields you can select the functiosn you wish to generate. You can play around with constant data, linear functions, parabolas and cubic functions on the left. These are all polynomial and very trivial to analyse. If you select more than one the data will be split between these functions with sharp transitions. In the graphs below you should be able to see these functions, and below that an anlysis. Parabolic sections in the data, for example, will be marked as <code>3</code> (2+1). Note, when you select more than one function, how the transition between them is marked in the second graph as <code>0</code>. This identifies it as a <code>point of interest</code>, where something interesting happens and further analysis should be conducted.</p>
<p>After familiarising yourself with the interface and its polynomials, you can try some options on the right. First, the bottom two options <code>Bias</code> and  <code>Noise</code> will affect what data you are already generating. You can try generating a <code>Constant</code> with some <code>Noise</code>, and you may see slight variations within the data points. Try adjusting the <code>precision</code> above to see how the analyser reacts to noise at different settings.</p>
<p>The <code>Bias</code> option introduces a jump in the data points, something that is common when sensors are knocked off their true setting, or some  other imperfection in the sensor occurs suddenly. You may call this a <em>systematic error</em>. All data points after the jump are shifted up or down by some constant value. The analyser detects this and marks the point of the jump as a <code>point of interest</code>. for further analysis, where it can be easily adjusted for.</p>
<p>You can see how the analyser reacts to completely random data with the <code>Random</code> option. It may attempt to find some patterns in the chaos, but by enlarge should classify it as <code>undefined</code> at <code>-5</code>. You can probably appreciate the role of <code>precision</code> if you try setting it to <code>100</code> on <code>Random</code>.</p>
<p>Lastly, there is an option to generate exponentiall data. The <code>Exponential</code> option is not very kind to the analyser though. First of all, it is impossible to display it well on the first graph. Secondly, exponentials <em>level off</em> in a way that makes them indistinguishable from a constant value. This is a mathematical property that the analyser can do nothing about. But most significantly, it can be difficult for the analyser to handle the large number differences in the truly exponential segments of data. They may be classified as <code>Exponential</code> at <code>-1</code>, but more often than not will simply be <code>undefined</code>. This may have something to do with floating point precision and could be accomodated, at great cost to the processing power, with more precise data types (perhaps Java’s <code>BigDecimal</code> class), but is currently considered imperfect.</p>
<h2 id="source-code">Source Code</h2>
<p>Currently the code can be found <a href="https://github.com/SonkeWohler/analyser">on my GitHub</a><sup class="footnote-ref"><a href="#fn5" id="fnref5">5</a></sup>, feel free to take a look.</p>
<h2 id="background">Background</h2>
<p>The idea behind this Project is to analyse data sets purely mathematically, making use of derivatives. If you are not familiar with derivatives try <a href="https://www.khanacademy.org/math/ap-calculus-ab/ab-differentiation-1-new/ab-2-1/v/derivative-as-a-concept">this Khan Academy presentation</a><sup class="footnote-ref"><a href="#fn6" id="fnref6">6</a></sup> or <a href="http://mathworld.wolfram.com/Derivative.html">this Wolfram article</a><sup class="footnote-ref"><a href="#fn7" id="fnref7">7</a></sup>.  Of course here a numerical derivative is used.</p>
<p>This analysis works on a low level, analysing trace by trace, and is intended to provide information that may later be used by higher level analysis, which would grow increasingly complex.</p>
<p>It simply takes the derivative between data points, then the derivative between derivative points, and so on, typically to a depth of 10 (the <code>maxDepth</code>). If the data set follows a polynomial function the derivative will become zero eventually, and the depth where it does (the <code>derivDepth</code> will correspond to the degree of the polynomial, which tells you what the underlying equasion looks like. From here it is rather trivial to fit the correct equasion to the data.</p>
<p>Other functions, like the <a href="https://en.wikipedia.org/wiki/Exponential_function">exponential</a><sup class="footnote-ref"><a href="#fn8" id="fnref8">8</a></sup>, can be converted to polynomials, for example by taking the natural logarithm. This allows, theoretically, to classify a wide range of functions. As you can see above, problems do occur in this analysis, but it is principly possible.</p>
<p>I should point out that, rather than offering a solution to all functions, this method allows detecting polynomials very well, and offers some suggestions for exponentials and inverse functions. It is not intended to stand by itself, but rather fill out gaps between other existing methods of analysis. As stated previously, fitting data to a known curve is rather trivial, and has been implemented countless times, even in the famous <em>Excel</em>. Also, some functions, in particular <a href="http://mathworld.wolfram.com/Sine.html">trigonometric ones</a><sup class="footnote-ref"><a href="#fn9" id="fnref9">9</a></sup>, are better suited to <a href="http://www.thefouriertransform.com/">Fourier Transform</a><sup class="footnote-ref"><a href="#fn10" id="fnref10">10</a></sup> analysis, and would become very messy if annalysed with the here described method.</p>
<hr class="footnotes-sep">
<section class="footnotes">
<ol class="footnotes-list">
<li id="fn1" class="footnote-item"><p><a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html</a> <a href="#fnref1" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn2" class="footnote-item"><p><a href="https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-61460339-5500-40CC-9006-D4FC3FBCFC0D">https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-61460339-5500-40CC-9006-D4FC3FBCFC0D</a> <a href="#fnref2" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn3" class="footnote-item"><p><a href="https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8">https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8</a> <a href="#fnref3" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn4" class="footnote-item"><p><a href="https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE">https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE</a> <a href="#fnref4" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn5" class="footnote-item"><p><a href="https://github.com/SonkeWohler/analyser">https://github.com/SonkeWohler/analyser</a> <a href="#fnref5" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn6" class="footnote-item"><p><a href="https://www.khanacademy.org/math/ap-calculus-ab/ab-differentiation-1-new/ab-2-1/v/derivative-as-a-concept">https://www.khanacademy.org/math/ap-calculus-ab/ab-differentiation-1-new/ab-2-1/v/derivative-as-a-concept</a> <a href="#fnref6" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn7" class="footnote-item"><p><a href="http://mathworld.wolfram.com/Derivative.html">http://mathworld.wolfram.com/Derivative.html</a> <a href="#fnref7" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn8" class="footnote-item"><p><a href="https://en.wikipedia.org/wiki/Exponential_function">https://en.wikipedia.org/wiki/Exponential_function</a> <a href="#fnref8" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn9" class="footnote-item"><p><a href="http://mathworld.wolfram.com/Sine.html">http://mathworld.wolfram.com/Sine.html</a> <a href="#fnref9" class="footnote-backref">↩︎</a></p>
</li>
<li id="fn10" class="footnote-item"><p><a href="http://www.thefouriertransform.com/">http://www.thefouriertransform.com/</a> <a href="#fnref10" class="footnote-backref">↩︎</a></p>
</li>
</ol>
</section>

