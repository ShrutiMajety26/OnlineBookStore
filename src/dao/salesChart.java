package dao;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class salesChart extends ApplicationFrame 
{   
	private static final long serialVersionUID = 1L;
	public salesChart(final String title) 
    {
        super(title);
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        setContentPane(chartPanel);
    }
    private CategoryDataset createDataset() 
    {        
        // row keys...
        final String series1 = "Sales";
        final String category1 = "Jan";
        final String category2 = "Feb";
        final String category3 = "Mar";
        final String category4 = "Apr";        

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Integer> arr=new reportDao().getSalesByMonth();
        dataset.addValue(arr.get(0), series1, category1);
        dataset.addValue(arr.get(1), series1, category2);
        dataset.addValue(arr.get(2), series1, category3);
        dataset.addValue(arr.get(3), series1, category4);              
        return dataset;        
    }   
    private JFreeChart createChart(final CategoryDataset dataset)
    {       
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Monthly Sales for 671 Books",         // chart title
            "Month",               // domain axis label
            "Books purchased",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
     
        renderer.setSeriesPaint(0, gp0); 
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.        
        return chart;        
    }  
    public void windowClosing(final WindowEvent evt)
    {
    	if(evt.getWindow() == this)    	
    		dispose();    	
    }
}