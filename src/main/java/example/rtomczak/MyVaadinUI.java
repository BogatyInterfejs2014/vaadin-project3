package example.rtomczak;

import javax.servlet.annotation.WebServlet;

import org.atmosphere.cpr.Broadcaster;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
@Push
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI //implements Broadcaster.BroadcastListener
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "example.rtomczak.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    	final int[] votes = {0,0,0,0,0,0,0,0};
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("400px");
        chart.setHeight("300px");
                
        // Modify the default configuration a bit
        final Configuration conf = chart.getConfiguration();
        conf.setTitle("Voting");
        conf.setSubTitle("Votes for europarlament");
        conf.getLegend().setEnabled(false); // Disable legend

        

        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Korwin", "Kalisz",   "Miller",
                            "Palikot",    "Tusk", "Kaczyński",
                            "Napieralski",  "Pawlak");
        xaxis.setTitle("Partie");
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Votes");
        yaxis.getLabels().setFormatter(
          "function() {return Math.floor(this.value/1);}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
     // The data
        final ListSeries series = new ListSeries("Votes");
        series.setData(0,  0,  0,
                       0,  0, 0,
                       0, 0);
        conf.addSeries(series);
        /*Number[] numbers = series.getData();
        int number0 = (Integer) numbers[0];
        votes[0] = number0;*/
        //form
        
        // Have some layout
        final FormLayout form = new FormLayout();
        
        final TextField korwin = new TextField("Korwin");korwin.setValue("0");
        final TextField kalisz = new TextField("Kalisz");kalisz.setValue("0");
        final TextField miller = new TextField("Miller");miller.setValue("0");
        final TextField palikot = new TextField("Plikot");palikot.setValue("0");
        final TextField tusk = new TextField("Tusk");tusk.setValue("0");
        final TextField kaczynski = new TextField("Kaczynski");kaczynski.setValue("0");
        final TextField napieralski = new TextField("Napieralski");napieralski.setValue("0");
        final TextField pawlak = new TextField("Pawlak");pawlak.setValue("0");
        form.addComponent(korwin);
        form.addComponent(kalisz);
        form.addComponent(miller);
        form.addComponent(palikot);
        form.addComponent(tusk);
        form.addComponent(kaczynski);
        form.addComponent(napieralski);
        form.addComponent(pawlak);
        
        Button button = new Button("Add vote to chart");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	votes[0] += Integer.parseInt(korwin.getValue());
                votes[1] += Integer.parseInt(kalisz.getValue());
                votes[2] += Integer.parseInt(miller.getValue());
                votes[3] += Integer.parseInt(palikot.getValue());
                votes[4] += Integer.parseInt(tusk.getValue());
                votes[5] += Integer.parseInt(kaczynski.getValue());
                votes[6] += Integer.parseInt(napieralski.getValue());
                votes[7] += Integer.parseInt(pawlak.getValue());
                series.updatePoint(0, votes[0]);
                series.updatePoint(1, votes[1]);
                series.updatePoint(2, votes[2]);
                series.updatePoint(3, votes[3]);
                series.updatePoint(4, votes[4]);
                series.updatePoint(5, votes[5]);
                series.updatePoint(6, votes[6]);
                series.updatePoint(7, votes[7]);
                conf.addSeries(series);
                korwin.setValue("0");
                kalisz.setValue("0");
                miller.setValue("0");
                palikot.setValue("0");
                tusk.setValue("0");
                kaczynski.setValue("0");
                napieralski.setValue("0");
                pawlak.setValue("0");
            }
        });
        layout.addComponent(chart);
        layout.addComponent(form);
        layout.addComponent(button);
    }

}
