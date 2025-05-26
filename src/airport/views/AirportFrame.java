/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package airport.views;

import airport.controllers.AirportController;
import airport.controllers.FlightController;
import airport.controllers.LocationController;
import airport.controllers.PassengerController;
import airport.controllers.PlaneController;
import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.models.Location;
import airport.models.Flight;
import airport.models.Passenger;
import airport.models.Plane;
import airport.models.services.FlightService;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edangulo
 */
public class AirportFrame extends javax.swing.JFrame {

    /**
     * Creates new form AirportFrame
     */
    private int x, y;
    private ArrayList<Passenger> passengers;
    private ArrayList<Plane> planes;
    private ArrayList<Location> locations;
    private ArrayList<Flight> flights;

    public AirportFrame() {
        initComponents();
        initCustomComponents();

        this.passengers = new ArrayList<>();
        this.planes = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.flights = new ArrayList<>();

        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);

        this.generateMonths();
        this.generateDays();
        this.generateHours();
        this.generateMinutes();
        this.blockPanels();

    }

    private void blockPanels() {
        //9, 11
        for (int i = 1; i < navigablePane.getTabCount(); i++) {
            if (i != 9 && i != 11) {
                navigablePane.setEnabledAt(i, false);
            }
        }
    }

    private void generateMonths() {
        for (int i = 1; i < 13; i++) {
            MonthSelector.addItem("" + i);
            DepartureMonthSelection.addItem("" + i);
            updateBirthMonthSelection.addItem("" + i);
        }
    }

    private void generateDays() {
        for (int i = 1; i < 32; i++) {
            daySelector.addItem("" + i);
            departureDaySelection.addItem("" + i);
            updateBirthDaySelection.addItem("" + i);
        }
    }

    private void generateHours() {
        for (int i = 0; i < 24; i++) {
            departureHourSelection.addItem("" + i);
            hourSelection.addItem("" + i);
            durationHourSelection.addItem("" + i);
            hoursToDelaySelection.addItem("" + i);
        }
    }

    private void generateMinutes() {
        for (int i = 0; i < 60; i++) {
            departureMinuteSelection.addItem("" + i);
            minuteSelection.addItem("" + i);
            durationMinuteSelection.addItem("" + i);
            minutesToDelaySelection.addItem("" + i);
        }
    }

    private void initCustomComponents() {
        addPlaneComboBoxListener(PlaneSelection);
        addPassengerComboBoxListener(userSelect);
        addLocationComboBoxListener(DepartureLocationSelection);
        addLocationComboBoxListener(arrivalLocationSelection);
        addLocationComboBoxListener(scaleLocationSelection);
        addFlightComboBoxListener(FlightToAddSelection);
        addFlightByIdComboBoxListener(IDToDelaySelection);
    }

    private void addLocationComboBoxListener(JComboBox<String> comboBox) {
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                try {
                    List<Location> locations = AirportController.getLocationsOrderedById();
                    fillLocationSelectComboBox(comboBox, locations);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    private void addPlaneComboBoxListener(JComboBox<String> comboBox) {
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                try {
                    List<Plane> planes = AirportController.getPlanesOrderedById();
                    fillPlaneSelectComboBox(comboBox, planes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    private void addPassengerComboBoxListener(JComboBox<String> comboBox) {
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                try {
                    List<Passenger> passengers = AirportController.getPassengersOrderedById();
                    fillUserSelectComboBox(comboBox, passengers);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }
    private void addFlightComboBoxListener(JComboBox<String> comboBox) {
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                try {
                    List<Flight> flights = AirportController.getFlightsOrderedByDepartureDate();
                    fillFlightComboBox(comboBox, flights);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }
    private void addFlightByIdComboBoxListener(JComboBox<String> comboBox) {
        comboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                try {
                    List<Flight> flights = AirportController.getFlightsOrderedById();
                    fillFlightComboBox(comboBox, flights);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    public void fillUserSelectComboBox(JComboBox<String> comboBox, List<Passenger> passengers) {
        userSelect.removeAllItems();
        userSelect.addItem("Select User");
        for (Passenger p : passengers) {
            userSelect.addItem(String.valueOf(p.getId()));
        }
    }

    public void fillPlaneSelectComboBox(JComboBox<String> comboBox, List<Plane> planes) {
        PlaneSelection.removeAllItems();
        PlaneSelection.addItem("Select Plane");
        for (Plane p : planes) {
            PlaneSelection.addItem(p.getId());
        }
    }

    public void fillLocationSelectComboBox(JComboBox<String> comboBox, List<Location> locations) {
        comboBox.removeAllItems();
        comboBox.addItem("Select Location");
        for (Location loc : locations) {
            comboBox.addItem(loc.getAirportId());
        }
    }

    public void fillFlightComboBox(JComboBox<String> comboBox, List<Flight> flights) {
        comboBox.removeAllItems();
        comboBox.addItem("Select Flight");
        for (Flight f : flights) {
            comboBox.addItem(f.getId());
        }
    }
    public void fillFlightComboBoxByID(JComboBox<String> comboBox, List<Flight> flights) {
        comboBox.removeAllItems();
        comboBox.addItem("Select Flight");
        for (Flight f : flights) {
            comboBox.addItem(f.getId());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        absolutePanel = new airport.views.PanelRound();
        panelRound2 = new airport.views.PanelRound();
        closeButton = new javax.swing.JButton();
        navigablePane = new javax.swing.JTabbedPane();
        adminPanel = new javax.swing.JPanel();
        userOption = new javax.swing.JRadioButton();
        administratorOption = new javax.swing.JRadioButton();
        userSelect = new javax.swing.JComboBox<>();
        passengerRegistrationPanel = new javax.swing.JPanel();
        countryText = new javax.swing.JLabel();
        passengeridText = new javax.swing.JLabel();
        firstNameText = new javax.swing.JLabel();
        lastNameText = new javax.swing.JLabel();
        birthdateText = new javax.swing.JLabel();
        plus = new javax.swing.JLabel();
        countryPhoneCodeTextField = new javax.swing.JTextField();
        passengerIDTextField = new javax.swing.JTextField();
        yearTextField = new javax.swing.JTextField();
        CountryTextField = new javax.swing.JTextField();
        PhoneNumberTextField = new javax.swing.JTextField();
        PhoneText = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        LastNameTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        MonthSelector = new javax.swing.JComboBox<>();
        firstnameTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        daySelector = new javax.swing.JComboBox<>();
        registerButton = new javax.swing.JButton();
        airplaneRegistrationPanel = new javax.swing.JPanel();
        airplaneIDLabel = new javax.swing.JLabel();
        airplaneIDTextField = new javax.swing.JTextField();
        brandLabel = new javax.swing.JLabel();
        brandTextField = new javax.swing.JTextField();
        modelTextField = new javax.swing.JTextField();
        modelLabel = new javax.swing.JLabel();
        maxcapacityTextField = new javax.swing.JTextField();
        maxcapacityLabel = new javax.swing.JLabel();
        airlineTextField = new javax.swing.JTextField();
        airlineLabel = new javax.swing.JLabel();
        createAirplaneButton = new javax.swing.JButton();
        locationRegistrationPanel = new javax.swing.JPanel();
        locationAirportIDPanel = new javax.swing.JLabel();
        locationAirportIDTextField = new javax.swing.JTextField();
        airportNameLabel = new javax.swing.JLabel();
        airportNameTextField = new javax.swing.JTextField();
        airportCityTextField = new javax.swing.JTextField();
        airportCityLabel = new javax.swing.JLabel();
        airportCountryLabel = new javax.swing.JLabel();
        airportCountryTextField = new javax.swing.JTextField();
        latitudeTextField = new javax.swing.JTextField();
        latitudeLabel = new javax.swing.JLabel();
        longitudeLabel = new javax.swing.JLabel();
        longitudeTextField = new javax.swing.JTextField();
        createLocationButton = new javax.swing.JButton();
        flightRegistrationPanel = new javax.swing.JPanel();
        flightIDLabel = new javax.swing.JLabel();
        flightIDTextField = new javax.swing.JTextField();
        PlaneLabel = new javax.swing.JLabel();
        PlaneSelection = new javax.swing.JComboBox<>();
        DepartureLocationSelection = new javax.swing.JComboBox<>();
        departureLocationLabel = new javax.swing.JLabel();
        arrivalLocationSelection = new javax.swing.JComboBox<>();
        arrivalLocationLabel = new javax.swing.JLabel();
        scaleLocationLabel = new javax.swing.JLabel();
        scaleLocationSelection = new javax.swing.JComboBox<>();
        scaleDurationLabel = new javax.swing.JLabel();
        durationLabel = new javax.swing.JLabel();
        flightDepartureDateLabel = new javax.swing.JLabel();
        departureYearTextField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        DepartureMonthSelection = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        departureDaySelection = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        departureHourSelection = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        departureMinuteSelection = new javax.swing.JComboBox<>();
        hourSelection = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        minuteSelection = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        durationHourSelection = new javax.swing.JComboBox<>();
        durationMinuteSelection = new javax.swing.JComboBox<>();
        createFlightButton = new javax.swing.JButton();
        updateInfoPanel = new javax.swing.JPanel();
        updateIDLabel = new javax.swing.JLabel();
        updateIDTextField = new javax.swing.JTextField();
        updateFirstNameLabel = new javax.swing.JLabel();
        updateFirstNameTextField = new javax.swing.JTextField();
        updateLastNameLabel = new javax.swing.JLabel();
        updateLastNameTextField = new javax.swing.JTextField();
        updateBirthdateLabel = new javax.swing.JLabel();
        updateBirthYearTextField = new javax.swing.JTextField();
        updateBirthMonthSelection = new javax.swing.JComboBox<>();
        updateBirthDaySelection = new javax.swing.JComboBox<>();
        updatePhonenNumberTextField = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        updatePhoneCodeTextField = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        updatePhoneLabel = new javax.swing.JLabel();
        updateCountryLabel = new javax.swing.JLabel();
        updateCountryTextField = new javax.swing.JTextField();
        UpdateInfoButton = new javax.swing.JButton();
        addToFlightPanel = new javax.swing.JPanel();
        addFromIDTextField = new javax.swing.JTextField();
        addFromIDLabel = new javax.swing.JLabel();
        FlightToAddLabel = new javax.swing.JLabel();
        FlightToAddSelection = new javax.swing.JComboBox<>();
        addToFlightButton = new javax.swing.JButton();
        ShowMyFligthsPanel = new javax.swing.JPanel();
        myFligthsShow = new javax.swing.JScrollPane();
        myFlightsTable = new javax.swing.JTable();
        refreshMyFlightsButton = new javax.swing.JButton();
        ShowAllPassengersPanel = new javax.swing.JPanel();
        passengersShow = new javax.swing.JScrollPane();
        passengersTable = new javax.swing.JTable();
        refreshPassengersButton = new javax.swing.JButton();
        showAllFlightsPanel = new javax.swing.JPanel();
        showFlights = new javax.swing.JScrollPane();
        flightsTable = new javax.swing.JTable();
        refreshFlightsButton = new javax.swing.JButton();
        showAllPlanesPanel = new javax.swing.JPanel();
        refreshPlanesButton = new javax.swing.JButton();
        showPlanes = new javax.swing.JScrollPane();
        planesTable = new javax.swing.JTable();
        showAllLocationsPanel = new javax.swing.JPanel();
        showLocations = new javax.swing.JScrollPane();
        locationsTable = new javax.swing.JTable();
        refreshLocationsButton = new javax.swing.JButton();
        delayFlightPanel = new javax.swing.JPanel();
        hoursToDelaySelection = new javax.swing.JComboBox<>();
        hoursToDelayLabel = new javax.swing.JLabel();
        IDToDelayLabel = new javax.swing.JLabel();
        IDToDelaySelection = new javax.swing.JComboBox<>();
        minutesToDelayLabel = new javax.swing.JLabel();
        minutesToDelaySelection = new javax.swing.JComboBox<>();
        delayFlightButton = new javax.swing.JButton();
        panelRound3 = new airport.views.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        absolutePanel.setRadius(40);
        absolutePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        closeButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        closeButton.setText("X");
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(1083, Short.MAX_VALUE)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addComponent(closeButton)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        absolutePanel.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        navigablePane.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        adminPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userOption.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userOption.setText("User");
        userOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userOptionActionPerformed(evt);
            }
        });
        adminPanel.add(userOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, -1, -1));

        administratorOption.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        administratorOption.setText("Administrator");
        administratorOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administratorOptionActionPerformed(evt);
            }
        });
        adminPanel.add(administratorOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 164, -1, -1));

        userSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select User" }));
        userSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSelectActionPerformed(evt);
            }
        });
        adminPanel.add(userSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 130, -1));

        navigablePane.addTab("Administration", adminPanel);

        passengerRegistrationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        countryText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        countryText.setText("Country:");
        passengerRegistrationPanel.add(countryText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        passengeridText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengeridText.setText("ID:");
        passengerRegistrationPanel.add(passengeridText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        firstNameText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        firstNameText.setText("First Name:");
        passengerRegistrationPanel.add(firstNameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        lastNameText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lastNameText.setText("Last Name:");
        passengerRegistrationPanel.add(lastNameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        birthdateText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        birthdateText.setText("Birthdate:");
        passengerRegistrationPanel.add(birthdateText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        plus.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        plus.setText("+");
        passengerRegistrationPanel.add(plus, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 20, -1));

        countryPhoneCodeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(countryPhoneCodeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 50, -1));

        passengerIDTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(passengerIDTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 130, -1));

        yearTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(yearTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 90, -1));

        CountryTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(CountryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, -1));

        PhoneNumberTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(PhoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 130, -1));

        PhoneText.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PhoneText.setText("Phone:");
        passengerRegistrationPanel.add(PhoneText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel8.setText("-");
        passengerRegistrationPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 30, -1));

        LastNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(LastNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 130, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setText("-");
        passengerRegistrationPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 30, -1));

        MonthSelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MonthSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        MonthSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonthSelectorActionPerformed(evt);
            }
        });
        passengerRegistrationPanel.add(MonthSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, -1, -1));

        firstnameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerRegistrationPanel.add(firstnameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 130, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel10.setText("-");
        passengerRegistrationPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 30, -1));

        daySelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        daySelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));
        passengerRegistrationPanel.add(daySelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, -1));

        registerButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        passengerRegistrationPanel.add(registerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        navigablePane.addTab("Passenger registration", passengerRegistrationPanel);

        airplaneRegistrationPanel.setLayout(null);

        airplaneIDLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airplaneIDLabel.setText("ID:");
        airplaneRegistrationPanel.add(airplaneIDLabel);
        airplaneIDLabel.setBounds(53, 96, 22, 25);

        airplaneIDTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airplaneRegistrationPanel.add(airplaneIDTextField);
        airplaneIDTextField.setBounds(180, 93, 130, 33);

        brandLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        brandLabel.setText("Brand:");
        airplaneRegistrationPanel.add(brandLabel);
        brandLabel.setBounds(53, 157, 50, 25);

        brandTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airplaneRegistrationPanel.add(brandTextField);
        brandTextField.setBounds(180, 154, 130, 33);

        modelTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airplaneRegistrationPanel.add(modelTextField);
        modelTextField.setBounds(180, 213, 130, 33);

        modelLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        modelLabel.setText("Model:");
        airplaneRegistrationPanel.add(modelLabel);
        modelLabel.setBounds(53, 216, 55, 25);

        maxcapacityTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airplaneRegistrationPanel.add(maxcapacityTextField);
        maxcapacityTextField.setBounds(180, 273, 130, 33);

        maxcapacityLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        maxcapacityLabel.setText("Max Capacity:");
        airplaneRegistrationPanel.add(maxcapacityLabel);
        maxcapacityLabel.setBounds(53, 276, 109, 25);

        airlineTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airplaneRegistrationPanel.add(airlineTextField);
        airlineTextField.setBounds(180, 333, 130, 33);

        airlineLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airlineLabel.setText("Airline:");
        airplaneRegistrationPanel.add(airlineLabel);
        airlineLabel.setBounds(53, 336, 70, 25);

        createAirplaneButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createAirplaneButton.setText("Create");
        createAirplaneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAirplaneButtonActionPerformed(evt);
            }
        });
        airplaneRegistrationPanel.add(createAirplaneButton);
        createAirplaneButton.setBounds(490, 480, 120, 40);

        navigablePane.addTab("Airplane registration", airplaneRegistrationPanel);

        locationAirportIDPanel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        locationAirportIDPanel.setText("Airport ID:");

        locationAirportIDTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        airportNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airportNameLabel.setText("Airport name:");

        airportNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        airportCityTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        airportCityLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airportCityLabel.setText("Airport city:");

        airportCountryLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        airportCountryLabel.setText("Airport country:");

        airportCountryTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        latitudeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        latitudeLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        latitudeLabel.setText("Airport latitude:");

        longitudeLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        longitudeLabel.setText("Airport longitude:");

        longitudeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        createLocationButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createLocationButton.setText("Create");
        createLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLocationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout locationRegistrationPanelLayout = new javax.swing.GroupLayout(locationRegistrationPanel);
        locationRegistrationPanel.setLayout(locationRegistrationPanelLayout);
        locationRegistrationPanelLayout.setHorizontalGroup(
            locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(locationRegistrationPanelLayout.createSequentialGroup()
                .addGroup(locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(locationRegistrationPanelLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(locationAirportIDPanel)
                            .addComponent(airportNameLabel)
                            .addComponent(airportCityLabel)
                            .addComponent(airportCountryLabel)
                            .addComponent(latitudeLabel)
                            .addComponent(longitudeLabel))
                        .addGap(80, 80, 80)
                        .addGroup(locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(longitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationAirportIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(airportNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(airportCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(airportCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(latitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(locationRegistrationPanelLayout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(createLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(515, 515, 515))
        );
        locationRegistrationPanelLayout.setVerticalGroup(
            locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(locationRegistrationPanelLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(locationRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(locationAirportIDPanel)
                        .addGap(36, 36, 36)
                        .addComponent(airportNameLabel)
                        .addGap(34, 34, 34)
                        .addComponent(airportCityLabel)
                        .addGap(35, 35, 35)
                        .addComponent(airportCountryLabel)
                        .addGap(35, 35, 35)
                        .addComponent(latitudeLabel))
                    .addGroup(locationRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(locationAirportIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(airportNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(airportCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(airportCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(latitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(locationRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(longitudeLabel)
                    .addComponent(longitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(createLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        navigablePane.addTab("Location registration", locationRegistrationPanel);

        flightIDLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightIDLabel.setText("ID:");

        flightIDTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        PlaneLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneLabel.setText("Plane:");

        PlaneSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plane" }));
        PlaneSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlaneSelectionActionPerformed(evt);
            }
        });

        DepartureLocationSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureLocationSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));
        DepartureLocationSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepartureLocationSelectionActionPerformed(evt);
            }
        });

        departureLocationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureLocationLabel.setText("Departure location:");

        arrivalLocationSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        arrivalLocationSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        arrivalLocationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        arrivalLocationLabel.setText("Arrival location:");

        scaleLocationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scaleLocationLabel.setText("Scale location:");

        scaleLocationSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scaleLocationSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        scaleDurationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scaleDurationLabel.setText("Duration:");

        durationLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        durationLabel.setText("Duration:");

        flightDepartureDateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightDepartureDateLabel.setText("Departure date:");

        departureYearTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setText("-");

        DepartureMonthSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureMonthSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel31.setText("-");

        departureDaySelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureDaySelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setText("-");

        departureHourSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureHourSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setText("-");

        departureMinuteSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureMinuteSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        hourSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        hourSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setText("-");

        minuteSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        minuteSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setText("-");

        durationHourSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        durationHourSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        durationMinuteSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        durationMinuteSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));
        durationMinuteSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                durationMinuteSelectionActionPerformed(evt);
            }
        });

        createFlightButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createFlightButton.setText("Create");
        createFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFlightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout flightRegistrationPanelLayout = new javax.swing.GroupLayout(flightRegistrationPanel);
        flightRegistrationPanel.setLayout(flightRegistrationPanelLayout);
        flightRegistrationPanelLayout.setHorizontalGroup(
            flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(scaleLocationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scaleLocationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, flightRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(arrivalLocationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(arrivalLocationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(departureLocationLabel)
                        .addGap(46, 46, 46)
                        .addComponent(DepartureLocationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(flightIDLabel)
                            .addComponent(PlaneLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(flightIDTextField)
                            .addComponent(PlaneSelection, 0, 130, Short.MAX_VALUE))))
                .addGap(45, 45, 45)
                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scaleDurationLabel)
                    .addComponent(durationLabel)
                    .addComponent(flightDepartureDateLabel))
                .addGap(18, 18, 18)
                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addComponent(departureYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DepartureMonthSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(departureDaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(departureHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(departureMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                .addComponent(hourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(minuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                .addComponent(durationHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(durationMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, flightRegistrationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(530, 530, 530))
        );
        flightRegistrationPanelLayout.setVerticalGroup(
            flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(flightIDLabel))
                    .addComponent(flightIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlaneLabel)
                    .addComponent(PlaneSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(departureHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(departureMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(flightRegistrationPanelLayout.createSequentialGroup()
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(departureLocationLabel)
                                .addComponent(DepartureLocationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(flightDepartureDateLabel))
                            .addComponent(departureYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DepartureMonthSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(departureDaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(arrivalLocationLabel)
                                .addComponent(arrivalLocationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(durationLabel))
                            .addComponent(hourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(minuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(durationHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(durationMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(flightRegistrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(scaleLocationLabel)
                                .addComponent(scaleLocationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(scaleDurationLabel)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(createFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        navigablePane.addTab("Flight registration", flightRegistrationPanel);

        updateIDLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateIDLabel.setText("ID:");

        updateIDTextField.setEditable(false);
        updateIDTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateIDTextField.setEnabled(false);
        updateIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateIDTextFieldActionPerformed(evt);
            }
        });

        updateFirstNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateFirstNameLabel.setText("First Name:");

        updateFirstNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        updateLastNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateLastNameLabel.setText("Last Name:");

        updateLastNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        updateBirthdateLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateBirthdateLabel.setText("Birthdate:");

        updateBirthYearTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        updateBirthMonthSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateBirthMonthSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        updateBirthDaySelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateBirthDaySelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        updatePhonenNumberTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel40.setText("-");

        updatePhoneCodeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel41.setText("+");

        updatePhoneLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updatePhoneLabel.setText("Phone:");

        updateCountryLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateCountryLabel.setText("Country:");

        updateCountryTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        UpdateInfoButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        UpdateInfoButton.setText("Update");
        UpdateInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateInfoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updateInfoPanelLayout = new javax.swing.GroupLayout(updateInfoPanel);
        updateInfoPanel.setLayout(updateInfoPanelLayout);
        updateInfoPanelLayout.setHorizontalGroup(
            updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateInfoPanelLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                                .addComponent(updateIDLabel)
                                .addGap(108, 108, 108)
                                .addComponent(updateIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                                .addComponent(updateFirstNameLabel)
                                .addGap(41, 41, 41)
                                .addComponent(updateFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                                .addComponent(updateLastNameLabel)
                                .addGap(43, 43, 43)
                                .addComponent(updateLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                                .addComponent(updateBirthdateLabel)
                                .addGap(55, 55, 55)
                                .addComponent(updateBirthYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(updateBirthMonthSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(updateBirthDaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                                .addComponent(updatePhoneLabel)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(updatePhoneCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(updatePhonenNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                                .addComponent(updateCountryLabel)
                                .addGap(63, 63, 63)
                                .addComponent(updateCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(updateInfoPanelLayout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(UpdateInfoButton)))
                .addContainerGap(555, Short.MAX_VALUE))
        );
        updateInfoPanelLayout.setVerticalGroup(
            updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateInfoPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateIDLabel)
                    .addComponent(updateIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateFirstNameLabel)
                    .addComponent(updateFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateLastNameLabel)
                    .addComponent(updateLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateBirthdateLabel)
                    .addComponent(updateBirthYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBirthMonthSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBirthDaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updatePhoneLabel)
                    .addComponent(jLabel41)
                    .addComponent(updatePhoneCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(updatePhonenNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(updateInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateCountryLabel)
                    .addComponent(updateCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(UpdateInfoButton)
                .addGap(113, 113, 113))
        );

        navigablePane.addTab("Update info", updateInfoPanel);

        addFromIDTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addFromIDTextField.setEnabled(false);

        addFromIDLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addFromIDLabel.setText("ID:");

        FlightToAddLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FlightToAddLabel.setText("Flight:");

        FlightToAddSelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FlightToAddSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flight" }));

        addToFlightButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addToFlightButton.setText("Add");
        addToFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToFlightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addToFlightPanelLayout = new javax.swing.GroupLayout(addToFlightPanel);
        addToFlightPanel.setLayout(addToFlightPanelLayout);
        addToFlightPanelLayout.setHorizontalGroup(
            addToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addToFlightPanelLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(addToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addFromIDLabel)
                    .addComponent(FlightToAddLabel))
                .addGap(79, 79, 79)
                .addGroup(addToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FlightToAddSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFromIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(829, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addToFlightPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addToFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(509, 509, 509))
        );
        addToFlightPanelLayout.setVerticalGroup(
            addToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addToFlightPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(addToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addToFlightPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(addFromIDLabel))
                    .addComponent(addFromIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(addToFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FlightToAddLabel)
                    .addComponent(FlightToAddSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addComponent(addToFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        navigablePane.addTab("Add to flight", addToFlightPanel);

        myFlightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        myFlightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Departure Date", "Arrival Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        myFligthsShow.setViewportView(myFlightsTable);

        refreshMyFlightsButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        refreshMyFlightsButton.setText("Refresh");
        refreshMyFlightsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMyFlightsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowMyFligthsPanelLayout = new javax.swing.GroupLayout(ShowMyFligthsPanel);
        ShowMyFligthsPanel.setLayout(ShowMyFligthsPanelLayout);
        ShowMyFligthsPanelLayout.setHorizontalGroup(
            ShowMyFligthsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowMyFligthsPanelLayout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(myFligthsShow, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowMyFligthsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refreshMyFlightsButton)
                .addGap(527, 527, 527))
        );
        ShowMyFligthsPanelLayout.setVerticalGroup(
            ShowMyFligthsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowMyFligthsPanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(myFligthsShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(refreshMyFlightsButton)
                .addContainerGap())
        );

        navigablePane.addTab("Show my flights", ShowMyFligthsPanel);

        passengersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        passengersShow.setViewportView(passengersTable);

        refreshPassengersButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        refreshPassengersButton.setText("Refresh");
        refreshPassengersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPassengersButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowAllPassengersPanelLayout = new javax.swing.GroupLayout(ShowAllPassengersPanel);
        ShowAllPassengersPanel.setLayout(ShowAllPassengersPanelLayout);
        ShowAllPassengersPanelLayout.setHorizontalGroup(
            ShowAllPassengersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowAllPassengersPanelLayout.createSequentialGroup()
                .addGroup(ShowAllPassengersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowAllPassengersPanelLayout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(refreshPassengersButton))
                    .addGroup(ShowAllPassengersPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(passengersShow, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        ShowAllPassengersPanelLayout.setVerticalGroup(
            ShowAllPassengersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowAllPassengersPanelLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(passengersShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshPassengersButton)
                .addContainerGap())
        );

        navigablePane.addTab("Show all passengers", ShowAllPassengersPanel);

        flightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        showFlights.setViewportView(flightsTable);

        refreshFlightsButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        refreshFlightsButton.setText("Refresh");
        refreshFlightsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshFlightsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout showAllFlightsPanelLayout = new javax.swing.GroupLayout(showAllFlightsPanel);
        showAllFlightsPanel.setLayout(showAllFlightsPanelLayout);
        showAllFlightsPanelLayout.setHorizontalGroup(
            showAllFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showAllFlightsPanelLayout.createSequentialGroup()
                .addGroup(showAllFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showAllFlightsPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(showFlights, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(showAllFlightsPanelLayout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(refreshFlightsButton)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        showAllFlightsPanelLayout.setVerticalGroup(
            showAllFlightsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showAllFlightsPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(showFlights, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshFlightsButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        navigablePane.addTab("Show all flights", showAllFlightsPanel);

        refreshPlanesButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        refreshPlanesButton.setText("Refresh");
        refreshPlanesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPlanesButtonActionPerformed(evt);
            }
        });

        planesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Max Capacity", "Airline", "Number Flights"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        showPlanes.setViewportView(planesTable);

        javax.swing.GroupLayout showAllPlanesPanelLayout = new javax.swing.GroupLayout(showAllPlanesPanel);
        showAllPlanesPanel.setLayout(showAllPlanesPanelLayout);
        showAllPlanesPanelLayout.setHorizontalGroup(
            showAllPlanesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showAllPlanesPanelLayout.createSequentialGroup()
                .addGroup(showAllPlanesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showAllPlanesPanelLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(refreshPlanesButton))
                    .addGroup(showAllPlanesPanelLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(showPlanes, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        showAllPlanesPanelLayout.setVerticalGroup(
            showAllPlanesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, showAllPlanesPanelLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(showPlanes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(refreshPlanesButton)
                .addGap(17, 17, 17))
        );

        navigablePane.addTab("Show all planes", showAllPlanesPanel);

        locationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airport ID", "Airport Name", "City", "Country"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        showLocations.setViewportView(locationsTable);

        refreshLocationsButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        refreshLocationsButton.setText("Refresh");
        refreshLocationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshLocationsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout showAllLocationsPanelLayout = new javax.swing.GroupLayout(showAllLocationsPanel);
        showAllLocationsPanel.setLayout(showAllLocationsPanelLayout);
        showAllLocationsPanelLayout.setHorizontalGroup(
            showAllLocationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showAllLocationsPanelLayout.createSequentialGroup()
                .addGroup(showAllLocationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showAllLocationsPanelLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(refreshLocationsButton))
                    .addGroup(showAllLocationsPanelLayout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(showLocations, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        showAllLocationsPanelLayout.setVerticalGroup(
            showAllLocationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, showAllLocationsPanelLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(showLocations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(refreshLocationsButton)
                .addGap(17, 17, 17))
        );

        navigablePane.addTab("Show all locations", showAllLocationsPanel);

        hoursToDelaySelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        hoursToDelaySelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        hoursToDelayLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        hoursToDelayLabel.setText("Hours:");

        IDToDelayLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IDToDelayLabel.setText("ID:");

        IDToDelaySelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IDToDelaySelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID" }));

        minutesToDelayLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        minutesToDelayLabel.setText("Minutes:");

        minutesToDelaySelection.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        minutesToDelaySelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        delayFlightButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        delayFlightButton.setText("Delay");
        delayFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delayFlightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout delayFlightPanelLayout = new javax.swing.GroupLayout(delayFlightPanel);
        delayFlightPanel.setLayout(delayFlightPanelLayout);
        delayFlightPanelLayout.setHorizontalGroup(
            delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delayFlightPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(delayFlightPanelLayout.createSequentialGroup()
                        .addComponent(minutesToDelayLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minutesToDelaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(delayFlightPanelLayout.createSequentialGroup()
                        .addGroup(delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IDToDelayLabel)
                            .addComponent(hoursToDelayLabel))
                        .addGap(79, 79, 79)
                        .addGroup(delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hoursToDelaySelection, 0, 105, Short.MAX_VALUE)
                            .addComponent(IDToDelaySelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(820, 820, 820))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, delayFlightPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(delayFlightButton)
                .addGap(531, 531, 531))
        );
        delayFlightPanelLayout.setVerticalGroup(
            delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delayFlightPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDToDelayLabel)
                    .addComponent(IDToDelaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoursToDelayLabel)
                    .addComponent(hoursToDelaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(delayFlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minutesToDelayLabel)
                    .addComponent(minutesToDelaySelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                .addComponent(delayFlightButton)
                .addGap(33, 33, 33))
        );

        navigablePane.addTab("Delay flight", delayFlightPanel);

        absolutePanel.add(navigablePane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 41, 1150, 620));

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        absolutePanel.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 660, 1150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(absolutePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(absolutePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelRound2MousePressed

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelRound2MouseDragged

    private void administratorOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administratorOptionActionPerformed
        if (userOption.isSelected()) {
            userOption.setSelected(false);
            userSelect.setSelectedIndex(0);

        }
        for (int i = 1; i < navigablePane.getTabCount(); i++) {
            navigablePane.setEnabledAt(i, true);
        }
        navigablePane.setEnabledAt(5, false);
        navigablePane.setEnabledAt(6, false);
    }//GEN-LAST:event_administratorOptionActionPerformed

    private void userOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userOptionActionPerformed
        if (administratorOption.isSelected()) {
            administratorOption.setSelected(false);
        }
        for (int i = 1; i < navigablePane.getTabCount(); i++) {

            navigablePane.setEnabledAt(i, false);

        }
        navigablePane.setEnabledAt(9, true);
        navigablePane.setEnabledAt(5, true);
        navigablePane.setEnabledAt(6, true);
        navigablePane.setEnabledAt(7, true);
        navigablePane.setEnabledAt(11, true);
    }//GEN-LAST:event_userOptionActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        try {
            long id = Long.parseLong(passengerIDTextField.getText());
            String firstname = firstnameTextField.getText();
            String lastname = LastNameTextField.getText();
            int year = Integer.parseInt(yearTextField.getText());
            int month = Integer.parseInt(MonthSelector.getItemAt(MonthSelector.getSelectedIndex()));
            int day = Integer.parseInt(daySelector.getItemAt(daySelector.getSelectedIndex()));
            int phoneCode = Integer.parseInt(countryPhoneCodeTextField.getText());
            long phone = Long.parseLong(PhoneNumberTextField.getText());
            String country = CountryTextField.getText();

            LocalDate birthDate = LocalDate.of(year, month, day);

            Passenger newPassenger = new Passenger(id, firstname, lastname, birthDate, phoneCode, phone, country);

            Response response = PassengerController.createPassenger(newPassenger);
            if (response.getStatus() == Status.CREATED) {
                fillUserSelectComboBox(userSelect, AirportController.getPassengersOrderedById());
                this.passengers.add(newPassenger);
            } else {
                JOptionPane.showMessageDialog(this, "Error: " + response.getMessage());
            }
        } catch (Exception ex) {
            // Mostrar mensaje de error (opcional)
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    private void createAirplaneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAirplaneButtonActionPerformed
        // TODO add your handling code here:
        String id = airplaneIDTextField.getText();
        String brand = brandTextField.getText();
        String model = modelTextField.getText();
        int maxCapacity = Integer.parseInt(maxcapacityTextField.getText());
        String airline = airlineTextField.getText();
        Plane newPlane = new Plane(id, brand, model, maxCapacity, airline);
        Response response = PlaneController.createPlane(id, brand, model, maxCapacity, airline);
        if (response.getStatus() == Status.CREATED) {
            fillPlaneSelectComboBox(PlaneSelection, AirportController.getPlanesOrderedById());
            this.planes.add(newPlane);
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + response.getMessage());
        }
    }//GEN-LAST:event_createAirplaneButtonActionPerformed

    private void createLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createLocationButtonActionPerformed
        // TODO add your handling code here:
        String id = locationAirportIDTextField.getText();
        String name = airportNameTextField.getText();
        String city = airportCityTextField.getText();
        String country = airportCountryTextField.getText();
        double latitude = Double.parseDouble(latitudeTextField.getText());
        double longitude = Double.parseDouble(longitudeTextField.getText());
        Response response = LocationController.createLocation(id, name, city, country, latitude, longitude);
        if (response.getStatus() == Status.CREATED) {
            fillLocationSelectComboBox(DepartureLocationSelection, AirportController.getLocationsOrderedById());
            fillLocationSelectComboBox(arrivalLocationSelection, AirportController.getLocationsOrderedById());
            fillLocationSelectComboBox(scaleLocationSelection, AirportController.getLocationsOrderedById());
            this.locations.add(new Location(id, name, city, country, latitude, longitude));
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + response.getMessage());
        }

    }//GEN-LAST:event_createLocationButtonActionPerformed

    private void createFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFlightButtonActionPerformed
        // TODO add your handling code here:
        String id = flightIDTextField.getText();
        String planeId = PlaneSelection.getItemAt(PlaneSelection.getSelectedIndex());
        String departureLocationId = DepartureLocationSelection.getItemAt(DepartureLocationSelection.getSelectedIndex());
        String arrivalLocationId = arrivalLocationSelection.getItemAt(arrivalLocationSelection.getSelectedIndex());
        String scaleLocationId = scaleLocationSelection.getItemAt(scaleLocationSelection.getSelectedIndex());
        int year = Integer.parseInt(departureYearTextField.getText());
        int month = Integer.parseInt(DepartureMonthSelection.getItemAt(DepartureMonthSelection.getSelectedIndex()));
        int day = Integer.parseInt(departureDaySelection.getItemAt(departureDaySelection.getSelectedIndex()));
        int hour = Integer.parseInt(departureHourSelection.getItemAt(departureHourSelection.getSelectedIndex()));
        int minutes = Integer.parseInt(departureMinuteSelection.getItemAt(departureMinuteSelection.getSelectedIndex()));
        int hoursDurationsArrival = Integer.parseInt(hourSelection.getItemAt(hourSelection.getSelectedIndex()));
        int minutesDurationsArrival = Integer.parseInt(minuteSelection.getItemAt(minuteSelection.getSelectedIndex()));
        int hoursDurationsScale = Integer.parseInt(durationHourSelection.getItemAt(durationHourSelection.getSelectedIndex()));
        int minutesDurationsScale = Integer.parseInt(durationMinuteSelection.getItemAt(durationMinuteSelection.getSelectedIndex()));

        LocalDateTime departureDate = LocalDateTime.of(year, month, day, hour, minutes);

        Plane plane = null;
        for (Plane p : this.planes) {
            if (planeId.equals(p.getId())) {
                plane = p;
            }
        }

        Location departure = null;
        Location arrival = null;
        Location scale = null;
        for (Location location : this.locations) {
            if (departureLocationId.equals(location.getAirportId())) {
                departure = location;
            }
            if (arrivalLocationId.equals(location.getAirportId())) {
                arrival = location;
            }
            if (scaleLocationId.equals(location.getAirportId())) {
                scale = location;
            }
        }

        Response response = FlightController.createFlight(id, plane, scale, departure, arrival, departureDate, hoursDurationsArrival, minutesDurationsArrival, hoursDurationsScale, minutesDurationsScale);
        if (response.getStatus() == Status.CREATED) {
            if (scale == null) {
                fillFlightComboBox(FlightToAddSelection,AirportController.getFlightsOrderedByDepartureDate());
                this.flights.add(new Flight(id, plane, departure, arrival, departureDate, hoursDurationsArrival, minutesDurationsArrival));
            } else {
                fillFlightComboBox(FlightToAddSelection,AirportController.getFlightsOrderedByDepartureDate());
                this.flights.add(new Flight(id, plane, departure, scale, arrival, departureDate, hoursDurationsArrival, minutesDurationsArrival, hoursDurationsScale, minutesDurationsScale));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + response.getMessage());
        }

        this.FlightToAddSelection.addItem(id);
    }//GEN-LAST:event_createFlightButtonActionPerformed

    private void UpdateInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateInfoButtonActionPerformed
        // TODO add your handling code here:
        long id = Long.parseLong(updateIDTextField.getText());
        String firstname = updateFirstNameTextField.getText();
        String lastname = updateLastNameTextField.getText();
        int year = Integer.parseInt(updateBirthYearTextField.getText());
        int month = Integer.parseInt(MonthSelector.getItemAt(updateBirthMonthSelection.getSelectedIndex()));
        int day = Integer.parseInt(daySelector.getItemAt(updateBirthDaySelection.getSelectedIndex()));
        int phoneCode = Integer.parseInt(updatePhoneCodeTextField.getText());
        long phone = Long.parseLong(updatePhonenNumberTextField.getText());
        String country = updateCountryTextField.getText();

        LocalDate birthDate = LocalDate.of(year, month, day);

        Passenger updatedPassenger = new Passenger(id, firstname, lastname, birthDate, phoneCode, phone, country);

        PassengerController.updatePassenger(updatedPassenger);
        JOptionPane.showMessageDialog(this, "Información actualizada correctamente.");
    }//GEN-LAST:event_UpdateInfoButtonActionPerformed

    private void addToFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToFlightButtonActionPerformed
        // TODO add your handling code here:
        long passengerId = Long.parseLong(addFromIDTextField.getText());
        String flightId = FlightToAddSelection.getItemAt(FlightToAddSelection.getSelectedIndex());

        Passenger passenger = null;
        Flight flight = null;

        for (Passenger p : this.passengers) {
            if (p.getId() == passengerId) {
                passenger = p;
            }
        }

        for (Flight f : this.flights) {
            if (flightId.equals(f.getId())) {
                flight = f;
            }
        }

        passenger.addFlight(flight);
        flight.addPassenger(passenger);
    }//GEN-LAST:event_addToFlightButtonActionPerformed

    private void delayFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delayFlightButtonActionPerformed
        // TODO add your handling code here:
        String flightId = IDToDelaySelection.getItemAt(IDToDelaySelection.getSelectedIndex());
        int hours = Integer.parseInt(hoursToDelaySelection.getItemAt(hoursToDelaySelection.getSelectedIndex()));
        int minutes = Integer.parseInt(minutesToDelaySelection.getItemAt(minutesToDelaySelection.getSelectedIndex()));

        Flight flight = null;
        for (Flight f : this.flights) {
            if (flightId.equals(f.getId())) {
                flight = f;
            }
        }

        FlightService.delay(flight, hours, minutes);
    }//GEN-LAST:event_delayFlightButtonActionPerformed

    private void refreshMyFlightsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMyFlightsButtonActionPerformed
        // TODO add your handling code here:
        long passengerId = Long.parseLong(userSelect.getItemAt(userSelect.getSelectedIndex()));

        Passenger passenger = null;
        for (Passenger p : this.passengers) {
            if (p.getId() == passengerId) {
                passenger = p;
            }
        }

        ArrayList<Flight> flights = passenger.getFlights();
        DefaultTableModel model = (DefaultTableModel) myFlightsTable.getModel();
        model.setRowCount(0);
        for (Flight flight : flights) {
            model.addRow(new Object[]{flight.getId(), flight.getDepartureDate(), FlightService.calculateArrivalDate(flight)});
        }
    }//GEN-LAST:event_refreshMyFlightsButtonActionPerformed

    private void refreshPassengersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPassengersButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) passengersTable.getModel();
        model.setRowCount(0);
        for (Passenger passenger : this.passengers) {
            model.addRow(new Object[]{passenger.getId(), passenger.getFullname(), passenger.getBirthDate(), passenger.calculateAge(), passenger.generateFullPhone(), passenger.getCountry(), passenger.getNumFlights()});
        }
    }//GEN-LAST:event_refreshPassengersButtonActionPerformed

    private void refreshFlightsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshFlightsButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) flightsTable.getModel();
        model.setRowCount(0);
        for (Flight flight : this.flights) {
            model.addRow(new Object[]{flight.getId(), flight.getDepartureLocation().getAirportId(), flight.getArrivalLocation().getAirportId(), (flight.getScaleLocation() == null ? "-" : flight.getScaleLocation().getAirportId()), flight.getDepartureDate(), FlightService.calculateArrivalDate(flight), flight.getPlane().getId(), flight.getNumPassengers()});
        }
    }//GEN-LAST:event_refreshFlightsButtonActionPerformed

    private void refreshPlanesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPlanesButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) planesTable.getModel();
        model.setRowCount(0);
        for (Plane plane : this.planes) {
            model.addRow(new Object[]{plane.getId(), plane.getBrand(), plane.getModel(), plane.getMaxCapacity(), plane.getAirline(), plane.getNumFlights()});
        }
    }//GEN-LAST:event_refreshPlanesButtonActionPerformed

    private void refreshLocationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshLocationsButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) locationsTable.getModel();
        model.setRowCount(0);
        for (Location location : this.locations) {
            model.addRow(new Object[]{location.getAirportId(), location.getAirportName(), location.getAirportCity(), location.getAirportCountry()});
        }
    }//GEN-LAST:event_refreshLocationsButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void userSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSelectActionPerformed
        try {
            String id = (String) userSelect.getSelectedItem();
            if (id != null && !id.equals("Select User")) {
                updateIDTextField.setText(id);
                addFromIDTextField.setText(id);
            } else {
                updateIDTextField.setText("");
                addFromIDTextField.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_userSelectActionPerformed

    private void durationMinuteSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_durationMinuteSelectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_durationMinuteSelectionActionPerformed

    private void updateIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateIDTextFieldActionPerformed

    private void PlaneSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlaneSelectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PlaneSelectionActionPerformed

    private void MonthSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonthSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonthSelectorActionPerformed

    private void DepartureLocationSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartureLocationSelectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepartureLocationSelectionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AirportFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CountryTextField;
    private javax.swing.JComboBox<String> DepartureLocationSelection;
    private javax.swing.JComboBox<String> DepartureMonthSelection;
    private javax.swing.JLabel FlightToAddLabel;
    private javax.swing.JComboBox<String> FlightToAddSelection;
    private javax.swing.JLabel IDToDelayLabel;
    private javax.swing.JComboBox<String> IDToDelaySelection;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JComboBox<String> MonthSelector;
    private javax.swing.JTextField PhoneNumberTextField;
    private javax.swing.JLabel PhoneText;
    private javax.swing.JLabel PlaneLabel;
    private javax.swing.JComboBox<String> PlaneSelection;
    private javax.swing.JPanel ShowAllPassengersPanel;
    private javax.swing.JPanel ShowMyFligthsPanel;
    private javax.swing.JButton UpdateInfoButton;
    private airport.views.PanelRound absolutePanel;
    private javax.swing.JLabel addFromIDLabel;
    private javax.swing.JTextField addFromIDTextField;
    private javax.swing.JButton addToFlightButton;
    private javax.swing.JPanel addToFlightPanel;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JRadioButton administratorOption;
    private javax.swing.JLabel airlineLabel;
    private javax.swing.JTextField airlineTextField;
    private javax.swing.JLabel airplaneIDLabel;
    private javax.swing.JTextField airplaneIDTextField;
    private javax.swing.JPanel airplaneRegistrationPanel;
    private javax.swing.JLabel airportCityLabel;
    private javax.swing.JTextField airportCityTextField;
    private javax.swing.JLabel airportCountryLabel;
    private javax.swing.JTextField airportCountryTextField;
    private javax.swing.JLabel airportNameLabel;
    private javax.swing.JTextField airportNameTextField;
    private javax.swing.JLabel arrivalLocationLabel;
    private javax.swing.JComboBox<String> arrivalLocationSelection;
    private javax.swing.JLabel birthdateText;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JTextField brandTextField;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField countryPhoneCodeTextField;
    private javax.swing.JLabel countryText;
    private javax.swing.JButton createAirplaneButton;
    private javax.swing.JButton createFlightButton;
    private javax.swing.JButton createLocationButton;
    private javax.swing.JComboBox<String> daySelector;
    private javax.swing.JButton delayFlightButton;
    private javax.swing.JPanel delayFlightPanel;
    private javax.swing.JComboBox<String> departureDaySelection;
    private javax.swing.JComboBox<String> departureHourSelection;
    private javax.swing.JLabel departureLocationLabel;
    private javax.swing.JComboBox<String> departureMinuteSelection;
    private javax.swing.JTextField departureYearTextField;
    private javax.swing.JComboBox<String> durationHourSelection;
    private javax.swing.JLabel durationLabel;
    private javax.swing.JComboBox<String> durationMinuteSelection;
    private javax.swing.JLabel firstNameText;
    private javax.swing.JTextField firstnameTextField;
    private javax.swing.JLabel flightDepartureDateLabel;
    private javax.swing.JLabel flightIDLabel;
    private javax.swing.JTextField flightIDTextField;
    private javax.swing.JPanel flightRegistrationPanel;
    private javax.swing.JTable flightsTable;
    private javax.swing.JComboBox<String> hourSelection;
    private javax.swing.JLabel hoursToDelayLabel;
    private javax.swing.JComboBox<String> hoursToDelaySelection;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lastNameText;
    private javax.swing.JLabel latitudeLabel;
    private javax.swing.JTextField latitudeTextField;
    private javax.swing.JLabel locationAirportIDPanel;
    private javax.swing.JTextField locationAirportIDTextField;
    private javax.swing.JPanel locationRegistrationPanel;
    private javax.swing.JTable locationsTable;
    private javax.swing.JLabel longitudeLabel;
    private javax.swing.JTextField longitudeTextField;
    private javax.swing.JLabel maxcapacityLabel;
    private javax.swing.JTextField maxcapacityTextField;
    private javax.swing.JComboBox<String> minuteSelection;
    private javax.swing.JLabel minutesToDelayLabel;
    private javax.swing.JComboBox<String> minutesToDelaySelection;
    private javax.swing.JLabel modelLabel;
    private javax.swing.JTextField modelTextField;
    private javax.swing.JTable myFlightsTable;
    private javax.swing.JScrollPane myFligthsShow;
    private javax.swing.JTabbedPane navigablePane;
    private airport.views.PanelRound panelRound2;
    private airport.views.PanelRound panelRound3;
    private javax.swing.JTextField passengerIDTextField;
    private javax.swing.JPanel passengerRegistrationPanel;
    private javax.swing.JLabel passengeridText;
    private javax.swing.JScrollPane passengersShow;
    private javax.swing.JTable passengersTable;
    private javax.swing.JTable planesTable;
    private javax.swing.JLabel plus;
    private javax.swing.JButton refreshFlightsButton;
    private javax.swing.JButton refreshLocationsButton;
    private javax.swing.JButton refreshMyFlightsButton;
    private javax.swing.JButton refreshPassengersButton;
    private javax.swing.JButton refreshPlanesButton;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel scaleDurationLabel;
    private javax.swing.JLabel scaleLocationLabel;
    private javax.swing.JComboBox<String> scaleLocationSelection;
    private javax.swing.JPanel showAllFlightsPanel;
    private javax.swing.JPanel showAllLocationsPanel;
    private javax.swing.JPanel showAllPlanesPanel;
    private javax.swing.JScrollPane showFlights;
    private javax.swing.JScrollPane showLocations;
    private javax.swing.JScrollPane showPlanes;
    private javax.swing.JComboBox<String> updateBirthDaySelection;
    private javax.swing.JComboBox<String> updateBirthMonthSelection;
    private javax.swing.JTextField updateBirthYearTextField;
    private javax.swing.JLabel updateBirthdateLabel;
    private javax.swing.JLabel updateCountryLabel;
    private javax.swing.JTextField updateCountryTextField;
    private javax.swing.JLabel updateFirstNameLabel;
    private javax.swing.JTextField updateFirstNameTextField;
    private javax.swing.JLabel updateIDLabel;
    private javax.swing.JTextField updateIDTextField;
    private javax.swing.JPanel updateInfoPanel;
    private javax.swing.JLabel updateLastNameLabel;
    private javax.swing.JTextField updateLastNameTextField;
    private javax.swing.JTextField updatePhoneCodeTextField;
    private javax.swing.JLabel updatePhoneLabel;
    private javax.swing.JTextField updatePhonenNumberTextField;
    private javax.swing.JRadioButton userOption;
    private javax.swing.JComboBox<String> userSelect;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration//GEN-END:variables
}
