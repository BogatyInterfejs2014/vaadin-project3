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
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
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
    Chart chart;
    ListSeries series;
    int[] receiveds = new int[8];
    final int[] votes = {0,0,0,0,0,0,0,0};
    Navigator navigator;
    @Override
    protected void init(VaadinRequest request) {
    	
    	// Create a navigator to control the views
        navigator = new Navigator(this, this);
        
        // Create and register the views
        navigator.addView("", new Wykres());
        navigator.addView("Glosowanie", new Glosowanie());
        
    }
    public class Wykres extends VerticalLayout implements View{
    	public Wykres(){
    			setSizeFull();
				final VerticalLayout layout = new VerticalLayout();
		        layout.setMargin(true);
		        setContent(layout);
		        
		        chart = new Chart(ChartType.COLUMN);
		        chart.setWidth("400px");
		        chart.setHeight("300px");
		                
		        final Configuration conf = chart.getConfiguration();
		        conf.setTitle("Voting");
		        conf.setSubTitle("Votes for europarlament");
		        conf.getLegend().setEnabled(false); 
	
		        XAxis xaxis = new XAxis();
		        xaxis.setCategories("Korwin", "Kalisz",   "Miller",
		                            "Palikot",    "Tusk", "Kaczyński",
		                            "Napieralski",  "Pawlak");
		        xaxis.setTitle("Partie");
		        conf.addxAxis(xaxis);
	
		        YAxis yaxis = new YAxis();
		        yaxis.setTitle("Votes");
		        yaxis.getLabels().setFormatter(
		          "function() {return Math.floor(this.value/1);}");
		        yaxis.getLabels().setStep(2);
		        conf.addyAxis(yaxis);
		        series = new ListSeries("Votes");
		        series.setData(0,  0,  0,
		                       0,  0, 0,
		                       0, 0);
		        conf.addSeries(series);
		        
		        final TextField user = new TextField("Użytkownik");
	            final PasswordField password = new PasswordField("Haslo");
		        Button button1 = new Button("Go to votes");
		        button1.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if(user.getValue().equals("admin") && password.getValue().equals("AdMiN")){
							navigator.navigateTo("Glosowanie");
						}else{
							Notification.show("Wrong user or password");
							user.setValue("");
							password.setValue("");
						}	
						
					}
				});
		        
		        layout.addComponent(chart);
		        layout.addComponent(user);
		        layout.addComponent(password);
		        layout.addComponent(button1);
		        addComponent(layout);
		}

		@Override
		public void enter(ViewChangeEvent event) {
			//Notification.show("Welcome to Vaadin App");
		}
    	
    }
	public class Glosowanie extends VerticalLayout implements Broadcaster.BroadcastListener, View{   
    	
		private static final long serialVersionUID = 1L;
		public Glosowanie(){
			setSizeFull();
			final VerticalLayout layout1 = new VerticalLayout();
    		setContent(layout1);
            //form
            
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
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
                	votes[0] = 0;votes[1] = 0;votes[2] = 0;votes[3] = 0;
                    votes[4] = 0;votes[5] = 0;votes[6] = 0;votes[7] = 0;
                	votes[0] += Integer.parseInt(korwin.getValue());
                    votes[1] += Integer.parseInt(kalisz.getValue());
                    votes[2] += Integer.parseInt(miller.getValue());
                    votes[3] += Integer.parseInt(palikot.getValue());
                    votes[4] += Integer.parseInt(tusk.getValue());
                    votes[5] += Integer.parseInt(kaczynski.getValue());
                    votes[6] += Integer.parseInt(napieralski.getValue());
                    votes[7] += Integer.parseInt(pawlak.getValue());
                    Broadcaster.broadcast(votes);
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
            layout1.addComponent(form);
            layout1.addComponent(button);
            addComponent(layout1);
            Broadcaster.register(this);
            
    	}
		@Override
		public void receiveBroadcast(final int[] votes) {
			access(new Runnable() {
				@Override
	    		public void run() {
	    			
					receiveds[0] += votes[0];
					receiveds[1] += votes[1];
					receiveds[2] += votes[2];
					receiveds[3] += votes[3];
					receiveds[4] += votes[4];
					receiveds[5] += votes[5];
					receiveds[6] += votes[6];
					receiveds[7] += votes[7];
	    			final Configuration conf = chart.getConfiguration();
	                series.updatePoint(0, receiveds[0]);
	                series.updatePoint(1, receiveds[1]);
	                series.updatePoint(2, receiveds[2]);
	                series.updatePoint(3, receiveds[3]);
	                series.updatePoint(4, receiveds[4]);
	                series.updatePoint(5, receiveds[5]);
	                series.updatePoint(6, receiveds[6]);
	                series.updatePoint(7, receiveds[7]);
	                conf.addSeries(series);
	    		}
	    	});
		}
		@Override
		public void enter(ViewChangeEvent event) {
			//Notification.show("Welcome to Voting");
		}
    }
}
