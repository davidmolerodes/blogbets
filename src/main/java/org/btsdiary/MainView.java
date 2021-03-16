package org.btsdiary;


import back.Diary;
import back.LJson;
import back.Pick;
import com.google.gson.Gson;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * The main view contains a button and a click listener.
 */
@Route
@CssImport("./styles/style.css")
@CssImport(value = "./styles/grid.css", themeFor = "vaadin-grid")
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {

    private TextArea descripcion;
    private NumberField stake;
    private NumberField cuota;
    private TextField tipster;
    private TextField deporte;
    private TextField casa;
    private Button button;

    public MainView() throws IOException {
        Select<String> resultado = new Select<>();
        HorizontalLayout inicio = new HorizontalLayout();
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout a = new VerticalLayout();
        VerticalLayout b = new VerticalLayout();
        HorizontalLayout layout1 = new HorizontalLayout();
        HorizontalLayout layout2 = new HorizontalLayout();

        String json = "";
        BufferedReader br = new BufferedReader(new FileReader("/Users/deivddds/Desktop/blogbets/src/main/java/back/Diary.json"));
        String linea;
        while ((linea = br.readLine()) != null) {
            json += linea;
        }
        Gson gson = new Gson();
        Diary lista = gson.fromJson(json, Diary.class);

        Grid<Pick> grid = new Grid<>();
        Grid<Diary> grid2 = new Grid<>();

        NumberField unidad = new NumberField("Unidad");
        unidad.setValue(6d);
        unidad.setHasControls(true);
        unidad.setMin(1);
        unidad.setMax(100);

        NumberField bank = new NumberField("Bank Inicial");
        bank.setValue(1000d);
        bank.setHasControls(true);
        bank.setMin(1);
        bank.setMax(10000);

        inicio.add(unidad, bank);

        descripcion = new TextArea("Descripción");
        descripcion.setHeight("150px");
        descripcion.setWidth("460px");
        stake = new NumberField("Stake");
        cuota = new NumberField("Cuota");
        tipster = new TextField("Tipster");
        deporte = new TextField("Deporte");
        casa = new TextField("Casa");
        resultado = new Select();
        resultado.setLabel("W/L/V");
        resultado.setItems("W", "L", "V");
        button = new Button("AÑADIR");
        button.setThemeName("primary");
        button.setWidth("150px");
        button.setHeight("50px");

        b.setPadding(false);
        b.setMargin(false);

        layout.setPadding(false);
        layout.setMargin(false);

        layout1.setPadding(false);
        layout1.setMargin(false);

        b.add(stake, cuota);
        layout.add(descripcion,b);
        layout1.add(tipster, deporte, casa);
        layout2.add(casa, resultado);

        a.add(layout, layout1, layout2, button);

        button.addClickListener(e -> {
            Notification.show("Pick añadido");
        });

        Select<String> finalResultado = resultado;

        button.addClickListener(e -> {
            Pick t = new Pick(descripcion.getValue(), tipster.getValue(), finalResultado.getValue(), lista.getNum(), casa.getValue(), cuota.getValue(), deporte.getValue(), stake.getValue(), unidades(stake.getValue(), cuota.getValue(), finalResultado.getValue()));
            lista.create(t);
            grid.setItems(lista.getPicks());
            grid2.setItems(lista);
            LJson.GenerarJson(lista);
        });

        grid.setItems(lista.getPicks());
        grid.addColumn(Pick::getStake).setHeader("Stake").setWidth("100px");; //Columns created del grid
        grid.addColumn(Pick::getCuota).setHeader("Cuota").setWidth("100px");; //Columns created del grid
        grid.addColumn(Pick::getDescripcion).setHeader("Descripcion").setWidth("400px");
        grid.addColumn(Pick::getResultado).setHeader("W/L/V").setWidth("80px"); //Columns created del grid
        grid.addColumn(Pick::getU).setHeader("Und").setWidth("150px"); //Columns created del grid
        grid.addComponentColumn(item -> new Button("Delete", clickEvent -> {
            lista.delete(item.getId());
            grid.setItems(lista.getPicks());
            grid2.setItems(lista);
            LJson.GenerarJson(lista);
        })).setWidth("100px");

        grid.setClassNameGenerator(customer -> {
            if (customer.getU() < 0) {
                return "error";
            }
            else if (customer.getU() == 0){
                return "";
            }
            else{
                return "success";
            }
        });

        grid2.setItems(lista);
        grid2.setHeightByRows(true);
        grid2.addColumn(Diary::getNum).setHeader("Picks").setWidth("50px");; //Columns created del grid
        grid2.addColumn(Diary::getU).setHeader("Profit").setWidth("50px");; //Columns created del grid
        grid2.addColumn(Diary::getY).setHeader("Yield").setWidth("50px");; //Columns created del grid
        grid2.addColumn(aa -> lista.getB(unidad.getValue())).setHeader("Beneficio");
        grid2.addColumn(new NumberRenderer<>(
                Diary::getOdds, "%(,.2f")).setHeader("Odd Avg").setWidth("50px");; //Columns created del grid
        grid2.addColumn(new NumberRenderer<>(
                Diary::getStk, "%(,.2f")).setHeader("Stake Avg").setWidth("50px");; //Columns created del grid
        grid2.addColumn(bb -> lista.getB(unidad.getValue()) + bank.getValue()).setHeader("Bank Actual");
        grid2.setClassNameGenerator(customer -> {
            if (customer.getU() < 0) {
                return "error";
            }
            else{
                return "success";
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Home", e -> {
            add(grid2);
            remove(a);
            remove(grid);
        });

        menuBar.addItem("Picks", e -> {
            add(grid);
            remove(a);
            remove(grid2);
        });

        menuBar.addItem("Añadir", e -> {
            add(a);
            remove(grid);
            remove(grid2);
        });
        add(inicio);
        add(menuBar);
    }

    public double unidades(Double a, Double b, String c){
        double t;
        if (c.equals("W")){
            t = (a * b) - a;
        }
        else if (c.equals("L")){
            t = -a;
        }
        else
            t = 0;

        double roundDbl = Math.round(t*100.0)/100.0;
        return roundDbl;
    }
}
