import app.Main;
import dao.ExaminationDao;
import dao.PacientDao;
import dao.PsychiatristDao;
import dto.ExaminationDto;
import dto.PacientDto;
import dto.PsychiatristDto;
import entities.Examination;
import entities.Pacient;
import entities.Psychiatrist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    static  Logger LOGGER;
    public TextField pacient_first_name_filed;
    public TextField pacient_last_name_filed;
    public TextField pacient_age_filed;
    public TextField pacient_city_filed;
    public TextField pacient_rodne_cislo_filed;
    public Button pacient_create_button;
    public Button pacient_update_button;
    public Button pacient_delete_button;
    public TableView pacient_table;
    public TableColumn pacient_id_column;
    public TableColumn pacient_first_name_column;
    public TableColumn pacient_last_name_column;
    public TableColumn pacient_rodne_cislo_column;
    public TableColumn pacient_age_column;
    public TableColumn pacient_city_column;
    public TextField psychiatrist_first_name_field;
    public TextField psychiatrist_last_name_field;
    public TableView psychiatrist_table;
    public TableColumn psychiatrist_id_column;
    public TableColumn psychiatrist_first_name_column;
    public TableColumn psychiatrist_last_name_column;
    public TextField examination_pacient_id_filed;
    public TextField examination_psychiatrist_id_filed;
    public TextField examination_date_filed;
    public TextField examination_time_filed;
    public TextField examination_department_filed;
    public TextField examination_room_filed;
    public TableView examination_table;
    public TableColumn examination_id_column;
    public TableColumn examination_pacient_id;
    public TableColumn examination_psychiatrist_id;
    public TableColumn examination_date_column;
    public TableColumn examination_time_column;
    public TableColumn examination_department_column;
    public TableColumn examination_room_column;
    public Button examinetion_create_button;
    public Button examination_delete_button;
    public static final String VALIDATION_ERROR_CLASS = "validation-error";
    public Button examination_view_all_button;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private PacientDao pacientDao;
    private PsychiatristDao psychiatristDao;
    private ExaminationDao examinationDao;

    public Controller(){
    }

    /**
     * Check if all necessary fields are filled, otherwise highlights them.
     * **/

    private Optional<String> validateNotEmptyAndGet(TextField tf, Function<String, Boolean> validator) {
        tf.getStyleClass().removeAll(VALIDATION_ERROR_CLASS);
        String text = tf.getText();
        if (StringUtils.isNotEmpty(text)) {
            if (validator == null || validator.apply(text)) {
                return Optional.of(text);
            } else {
                tf.getStyleClass().add(VALIDATION_ERROR_CLASS);
                return Optional.empty();
            }
        } else {
            tf.getStyleClass().add(VALIDATION_ERROR_CLASS);
            return Optional.empty();
        }
    }

    /**
     * Check validity of input data.
     * **/

    private Boolean isAlpha(String value) {
        return value.matches("[A-Za-z]+");
    }

    private Boolean isNumbers(String value) {
        return value.matches("[0-9]+");
    }

    private Boolean isTime(String value) { return value.matches("(?:[0-1][0-9]|2[0-4]):[0-5]\\d"); }

    private Boolean isDate(String value) { return value.matches("\\d{4}\\-\\d{2}\\-\\d{2}"); }

    private Boolean notBeforeToday(String value) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(value);
            return date.after(new Date());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    @FXML
    void initialize(){
        pacientDao = new PacientDao();
        psychiatristDao = new PsychiatristDao();
        examinationDao = new ExaminationDao();

        LOGGER = Logger.getLogger(Main.class.getName());

        loadPacients();
        loadPsychiatrist();
        loadExaminations();

        /**
         * Create new pacient.
         * Action when "Create" button pushed - .
         *
         * **/

        pacient_create_button.setOnAction(e -> {
            Optional<String> firstName = validateNotEmptyAndGet(pacient_first_name_filed, this::isAlpha);
            Optional<String> lastName = validateNotEmptyAndGet(pacient_last_name_filed, this::isAlpha);
            Optional<String> rodneCislo = validateNotEmptyAndGet(pacient_rodne_cislo_filed, this::isNumbers);
            Optional<String> age = validateNotEmptyAndGet(pacient_age_filed, this::isNumbers);
            Optional<String> city = validateNotEmptyAndGet(pacient_city_filed, this::isAlpha );

            if (firstName.isPresent() &&
                    lastName.isPresent() && rodneCislo.isPresent() && age.isPresent() && city.isPresent()) {
                pacientDao.create(firstName.get(), lastName.get(),  rodneCislo.get(), age.get(), city.get());
                loadPacients();
            } else {
                System.out.println("Enter all pacient information");
            }
            loadPacients();

            pacient_first_name_filed.clear();
            pacient_last_name_filed.clear();
            pacient_rodne_cislo_filed.clear();
            pacient_age_filed.clear();
            pacient_city_filed.clear();

            examination_pacient_id_filed.clear();
            examination_psychiatrist_id_filed.clear();

//            LOGGER.log(Level.INFO, "Pacient is created");
        });

        /**
         * Update Pacient.
         * When "Update" pacient button pushed - all pacient's fields are updated.
         *
         * **/

        pacient_update_button.setOnAction(e -> {
            Optional<String> firstName = validateNotEmptyAndGet(pacient_first_name_filed, this::isAlpha);
            Optional<String> lastName = validateNotEmptyAndGet(pacient_last_name_filed, this::isAlpha);
            Optional<String> rodneCislo = validateNotEmptyAndGet(pacient_rodne_cislo_filed,this::isNumbers);
            Optional<String> age = validateNotEmptyAndGet(pacient_age_filed, this::isNumbers);
            Optional<String> city = validateNotEmptyAndGet(pacient_city_filed, this::isAlpha );

            if (firstName.isPresent() &&
                    lastName.isPresent() && rodneCislo.isPresent() && age.isPresent() && city.isPresent()) {
                PacientDto pacient = (PacientDto) pacient_table.getSelectionModel().getSelectedItem();
                if (pacient != null) {
                    Pacient pacientEntities = pacientDao.findById(pacient.getId()).get();
                    pacientDao.update(pacientEntities, firstName.get(), lastName.get(), rodneCislo.get(), age.get(), city.get());
                }
            }
            loadPacients();

            pacient_first_name_filed.clear();
            pacient_last_name_filed.clear();
            pacient_rodne_cislo_filed.clear();
            pacient_age_filed.clear();
            pacient_city_filed.clear();

            examination_pacient_id_filed.clear();
            examination_psychiatrist_id_filed.clear();

        });


        /**
         * Delete Pacient.
         * When "Delete" pacient button pushed - pacient and all his examinations are deleted.
         *
         * **/

        pacient_delete_button.setOnAction((ActionEvent e) -> {
            PacientDto pacientDto = (PacientDto) pacient_table.getSelectionModel().getSelectedItem();
            if (pacientDto != null) {
                pacientDao.findById(pacientDto.getId()).ifPresent(pacient -> {
                    examinationDao.findByPacient(pacient).forEach(examinationDao::delete);
                    pacientDao.delete(pacient);
                });
                loadExaminations();

                loadPacients();


                //LOGGER.log(Level.INFO, "Pacient is deleted");
            }

            pacient_first_name_filed.clear();
            pacient_last_name_filed.clear();
            pacient_rodne_cislo_filed.clear();
            pacient_age_filed.clear();
            pacient_city_filed.clear();

            examination_pacient_id_filed.clear();
            examination_psychiatrist_id_filed.clear();
        });

        /**
         * Action when certain PACIENT is selected.
         * Form on the left filled with PACIENT'S data.
         */
        pacient_table.setOnMouseClicked((MouseEvent e) -> {
            PacientDto pacient = (PacientDto) pacient_table.getSelectionModel().getSelectedItem();

            if (pacient != null) {
                pacient_first_name_filed.setText(pacient.getPacientFirstName());
                pacient_last_name_filed.setText(pacient.getPacientLastName());
                pacient_rodne_cislo_filed.setText(pacient.getRodneCislo());
                pacient_age_filed.setText(pacient.getAge());
                pacient_city_filed.setText(pacient.getCity());
                examination_pacient_id_filed.setText(String.valueOf(pacient.getId()));

                pacientDao.findById(pacient.getId()).ifPresent(this::loadExaminations);

            }
        });

        /**
         * Action when certain PSYCHIATRIST is selected.
         * Form on the left filled with PSYCHIATRIST'S data.
         */
        psychiatrist_table.setOnMouseClicked((MouseEvent e) -> {
            PsychiatristDto psychiatrist = (PsychiatristDto) psychiatrist_table.getSelectionModel().getSelectedItem();
            if (psychiatrist != null) {
                psychiatrist_first_name_field.setText(psychiatrist.getPsychiatristFirstName());
                psychiatrist_last_name_field.setText(psychiatrist.getPsychiatristLastName());
                examination_psychiatrist_id_filed.setText(String.valueOf(psychiatrist.getId()));

//                 psychiatristDao.findById(psychiatrist.getId()).ifPresent(this::loadExaminations);
            }

        });

        /**
         * Create new examination.
         * Action when "Create" button pushed - creates new examination.
         *
         * **/

        examinetion_create_button.setOnAction(e -> {
            Optional<String> date = validateNotEmptyAndGet(examination_date_filed, (val) -> isDate(val) && notBeforeToday(val));
            Optional<String> time = validateNotEmptyAndGet(examination_time_filed, this::isTime );
            Optional<String> department = validateNotEmptyAndGet(examination_department_filed, this::isNumbers);
            Optional<String> room = validateNotEmptyAndGet(examination_room_filed, this::isNumbers);

            if (date.isPresent() && time.isPresent() && department.isPresent() && room.isPresent()
                    && StringUtils.isNotEmpty(examination_pacient_id_filed.getText())
                    && StringUtils.isNotEmpty(examination_psychiatrist_id_filed.getText())) {
                long pacientID = Long.parseLong(examination_pacient_id_filed.getText());
                long psychiatristID = Long.parseLong(examination_psychiatrist_id_filed.getText());
                Pacient pacient = pacientDao.findById(pacientID)
                        .orElseThrow(() -> new RuntimeException("Client was not found by ID: " + pacient_id_column));
                Psychiatrist psychiatrist = psychiatristDao.findById(psychiatristID)
                        .orElseThrow(() -> new RuntimeException("Tour was not found by ID: " + psychiatrist_id_column));

                examinationDao.create(pacient, psychiatrist, date.get(), time.get(), department.get(), room.get());
                loadExaminations();
            } else {
                System.out.println("Enter all reservation information");
            }
//            examination_pacient_id_filed.clear();
//            examination_psychiatrist_id_filed.clear();
//            examination_date_filed.clear();
//            examination_time_filed.clear();
//            examination_department_filed.clear();
//            examination_room_filed.clear();

        });

        /**
         * Action when "View All" examination button pushed.
         * All examinations are visible.
         * **/
        
        examination_view_all_button.setOnAction((ActionEvent e) -> {
            loadExaminations();
        });
        
        

        /**
         * Action when "Delete" examination button pushed.
         * Deletes selected examination.
         * **/

            examination_delete_button.setOnAction((ActionEvent e) -> {
            ExaminationDto examinationDto = (ExaminationDto) examination_table.getSelectionModel().getSelectedItem();
            if (examinationDto != null) {
                examinationDao.findById(examinationDto.getId()).ifPresent(reservation -> {
                    examinationDao.delete(reservation);
                });
                loadExaminations();
            }
        });
    }



    private void loadPacients() {
        int selectedIndex = -1;
        if (pacient_table.getSelectionModel() != null) {
            selectedIndex = pacient_table.getSelectionModel().getSelectedIndex();
        }
        pacient_table.getItems().clear();
        List<Pacient> pacients = pacientDao.findAll();
        pacients.sort(Comparator.comparing(Pacient::getId));

        pacients.forEach(pacient -> pacient_table.getItems().add(new PacientDto(pacient)));

        if (selectedIndex != -1) {
            pacient_table.getSelectionModel().select(selectedIndex);
        }
    }

    private void loadPsychiatrist() {
        psychiatrist_table.getItems().clear();
        List<Psychiatrist> tours = psychiatristDao.findAll();
        tours.sort(Comparator.comparing(Psychiatrist::getId));
        tours.forEach(psychiatrist -> psychiatrist_table.getItems().add(new PsychiatristDto(psychiatrist)));
    }

    private void loadExaminations() {
        examination_table.getItems().clear();
        List<Examination> examinations = examinationDao.findAll();
        examinations.sort(Comparator.comparing(Examination::getId));
        examinations.forEach(examination -> examination_table.getItems().add(new ExaminationDto(examination)));
    }

    private void loadExaminations(Pacient pacient) {
        examination_table.getItems().clear();
        List<Examination> examinations = examinationDao.findByPacient(pacient);
        examinations.sort(Comparator.comparing(Examination::getId));
        examinations.forEach(examination -> examination_table.getItems().add(new ExaminationDto(examination)));
    }

    private void loadExaminations(Psychiatrist psychiatrist) {
        examination_table.getItems().clear();
        List<Examination> examinations = examinationDao.findByPsychiatrist(psychiatrist);
        examinations.sort(Comparator.comparing(Examination::getId));
        examinations.forEach(examination -> examination_table.getItems().add(new ExaminationDto(examination)));
    }
}
