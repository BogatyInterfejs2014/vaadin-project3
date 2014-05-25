package example.rtomczak;

import javax.servlet.annotation.WebServlet;

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
public class MyVaadinUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "example.rtomczak.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    	final int[] votes = new int[8];
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
          "function() {return Math.floor(this.value/10);}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
     // The data
        final ListSeries series = new ListSeries("Votes");
        series.setData(0,  0,  0,
                       0,  0, 0,
                       0, 0);
        conf.addSeries(series);
        Number[] numbers = series.getData();
        int number0 = (Integer) numbers[0];
        int number1 = (Integer) numbers[1];
        int number2 = (Integer) numbers[2];
        int number3 = (Integer) numbers[3];
        int number4 = (Integer) numbers[4];
        int number5 = (Integer) numbers[5];
        int number6 = (Integer) numbers[6];
        int number7 = (Integer) numbers[7];
          
        votes[0] = number0;
        votes[1] = number1;
        votes[2] = number2;
        votes[3] = number3;
        votes[4] = number4;
        votes[5] = number5;
        votes[6] = number6;
        votes[7] = number7;
        //form
        //Binding
        /*PropertysetItem item = new PropertysetItem();
        item.addItemProperty("korwin", new ObjectProperty<Integer>(number0));
        item.addItemProperty("kalisz", new ObjectProperty<Integer>(number1));
        item.addItemProperty("miller", new ObjectProperty<Integer>(number2));
        item.addItemProperty("palikot", new ObjectProperty<Integer>(number3));
        item.addItemProperty("tusk", new ObjectProperty<Integer>(number4));
        item.addItemProperty("kaczynski", new ObjectProperty<Integer>(number5));
        item.addItemProperty("napieralski", new ObjectProperty<Integer>(number6));
        item.addItemProperty("pawlak", new ObjectProperty<Integer>(number7));
*/
        // Have some layout
        final FormLayout form = new FormLayout();
        
        // Now create a binder that can also create the fields
        // using the default field factory
        /*FieldGroup binder = new FieldGroup(item);
        form.addComponent(binder.buildAndBind("Korwin", "korwin"));
        form.addComponent(binder.buildAndBind("Kalisz", "kalisz"));
        form.addComponent(binder.buildAndBind("Miller", "miller"));
        form.addComponent(binder.buildAndBind("Palikot", "palikot"));
        form.addComponent(binder.buildAndBind("Tusk", "tusk"));
        form.addComponent(binder.buildAndBind("Kaczynski", "kaczynski"));
        form.addComponent(binder.buildAndBind("Napieralski", "napieralski"));
        form.addComponent(binder.buildAndBind("Pawlak", "pawlak"));*/
        final TextField korwin = new TextField("Korwin");
        final TextField kalisz = new TextField("Kalisz");
        form.addComponent(korwin);
        form.addComponent(kalisz);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	votes[0] = Integer.parseInt(korwin.getValue());
                votes[1] = Integer.parseInt(kalisz.getValue());
                series.updatePoint(0, votes[0]);
                series.updatePoint(1, votes[1]);
                conf.addSeries(series);
            }
        });
        layout.addComponent(chart);
        layout.addComponent(form);
        layout.addComponent(button);
    }

}
