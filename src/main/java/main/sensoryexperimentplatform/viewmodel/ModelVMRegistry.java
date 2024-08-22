package main.sensoryexperimentplatform.viewmodel;


import main.sensoryexperimentplatform.models.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
public class ModelVMRegistry {
    private static ModelVMRegistry instance;
    private static Map<Class<? extends Model>, Function<Model, ViewModel>> registry = new HashMap<>();
    private ModelVMRegistry(){
        registry.put(Start.class, model -> new StartVM((Start) model));
        registry.put(Vas.class, model -> new VasStage_VM((Vas) model));
        registry.put(Notice.class, model -> new NoticeStage_VM((Notice) model));
        registry.put(gLMS.class, model -> new GLMSStage_VM((gLMS) model));
        registry.put(Input.class, model -> new InputStage_VM((Input) model));
        registry.put(Question.class, model -> new QuestionStage_VM((Question) model));
        registry.put(Timer.class, model -> new TimerStage_VM((Timer) model));
        registry.put(RatingContainer.class, model -> new RatingContainer_VM((RatingContainer) model));
        registry.put(Course.class, model -> new AddCourseVM((Course) model));
        registry.put(conditionalStatement.class, model -> new ConditionalStatementVM((conditionalStatement) model));
        registry.put(TasteTest.class, model -> new AddTasteVM((TasteTest) model));
        registry.put(AudibleInstruction.class, model -> {
            try {
                return new AudibleSound_VM((AudibleInstruction) model);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public static ModelVMRegistry getInstance(){
        if(instance == null){
            instance = new ModelVMRegistry();
        }
        return instance;
    }
    public ViewModel getViewModel(Model model){
        Function<Model, ViewModel> viewModel = registry.get(model.getClass());
        if(viewModel != null){
            return viewModel.apply(model);
        }
        return null; //if no view model for that specific model
    }


}
