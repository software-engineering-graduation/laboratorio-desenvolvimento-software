package com.labssoft.roteiro01.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

@SpringBootTest
class TestCreateTask {

    @Test
    void should_create_new_task_with_default_status_in_progress() {
        Task task = new Task(
                new Faker(Locale.US).lorem().characters(100),
                new Faker(Locale.US).lorem().characters(5000));
        assertEquals(TaskStatus.InProgress, task.getStatus());
        assertEquals(TaskType.Free, task.getType());
        assertEquals(TaskPriority.Low, task.getPriority());
        assertEquals(null, task.getDueDate());
        assertEquals(null, task.getDueDays());
    }

    @ParameterizedTest
    @CsvSource({
            "T,  ",
            "Título 2, Descrição 2",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus rhoncus ultricies ante sit dolor., Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin eu enim sagittis, gravida nisl non, maximus magna. Phasellus vel nunc lorem. Vestibulum maximus nunc in dui dictum, a tincidunt lorem suscipit. Fusce ac fermentum eros. Quisque accumsan odio a felis luctus blandit. Integer a libero sapien. Donec tristique sagittis neque, a consectetur orci ornare in. Etiam feugiat magna et massa elementum ullamcorper. Nam a ex vulputate, rhoncus odio quis, tincidunt diam. Sed auctor ac libero quis bibendum. Duis finibus lorem et felis gravida tempus. Maecenas mollis purus et dictum laoreet. Cras non pretium ante. Proin aliquam dictum metus, vel finibus ex sagittis et.Morbi ut posuere lacus, at feugiat lorem. Sed at mattis velit. Praesent maximus, tellus eget tempor dignissim, turpis quam malesuada justo, ullamcorper vulputate nulla odio a est. Aliquam gravida purus velit, quis feugiat sem consectetur in. Nunc quam nisl, scelerisque venenatis ligula vitae, congue ullamcorper nulla. Maecenas ut justo ut nunc maximus dapibus sed sed lorem. Vestibulum augue turpis, pellentesque non diam ac, consequat condimentum elit. In viverra quis tortor sit amet ornare. Proin laoreet tempus ornare.Phasellus cursus suscipit rhoncus. Donec ullamcorper euismod ex quis volutpat. Fusce in ex tellus. Maecenas bibendum odio ac est sollicitudin iaculis. Morbi luctus fermentum massa, quis tincidunt eros malesuada id. Fusce vulputate posuere ex, id dictum ex suscipit rutrum. Nam quis dui ut turpis ullamcorper cursus. Sed quis dui eros. Morbi volutpat, sem et sagittis tempor, libero ligula pulvinar tortor, nec imperdiet ipsum diam eu risus. Morbi at purus eget mauris finibus tempus.Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed aliquet, leo eu gravida aliquam, odio lectus semper tellus, pulvinar vehicula dui sem sit amet ligula. Sed ultrices ultricies nibh eget pulvinar. Proin sapien dui, maximus eget dui a, viverra faucibus nibh. In ullamcorper pellentesque auctor. Sed metus turpis, gravida vel ornare ut, convallis at felis. Sed ut ante felis. Donec dictum diam a urna feugiat consectetur.Cras quis tortor sit amet nunc lobortis suscipit. Pellentesque vel dui risus. Sed mattis scelerisque urna sed aliquam. Etiam condimentum mi eget ultricies finibus. Proin et nulla facilisis, faucibus sapien id, venenatis tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse ipsum est, egestas vel elit a, feugiat ullamcorper purus. Quisque tristique ornare lacinia. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut pulvinar finibus pretium. Aliquam est elit, euismod sit amet interdum in, imperdiet ut nibh. Aliquam sagittis augue eu laoreet porta.Aliquam nulla lorem, vestibulum placerat consequat et, interdum sed ante. Integer rutrum mauris lacus. Curabitur vel finibus risus. Vivamus sed erat eget enim interdum faucibus imperdiet et sapien. Praesent lorem mi, auctor ac bibendum eu, condimentum sit amet sem. Aenean varius porta elit, quis feugiat ante placerat id. Aenean ut odio et metus maximus sollicitudin vel ut justo. Mauris fermentum a augue ut elementum. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce mollis fermentum aliquam. Nullam vel turpis eros. Phasellus aliquam nisl a metus congue, quis scelerisque metus egestas. Sed neque nibh, ornare vel odio sed, pellentesque convallis magna. Etiam accumsan mattis sagittis. Donec ante lacus, pulvinar non ullamcorper et, ullamcorper non sapien. Nam nec ipsum et arcu congue ultricies in non risus.Phasellus elementum sapien lorem, mollis bibendum metus efficitur ac. Nulla facilisi. Phasellus non ante fringilla diam facilisis vestibulum in at justo. Morbi malesuada semper massa, sed posuere tellus vulputate non. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Morbi auctor auctor felis eu pretium. Morbi tincidunt nec risus eu tincidunt. Ut viverra ex mauris, vel semper velit laoreet vitae. Fusce ut laoreet nunc. Nunc sem risus, congue dictum dui non, laoreet pellentesque dolor. Aenean luctus blandit tellus nec viverra. Donec non nisl in enim efficitur consequat. In finibus magna lectus, eu gravida libero tristique quis. Vivamus placerat augue eget vulputate imperdiet. Integer cursus urna vel nisl egestas, ac hendrerit turpis tincidunt.Integer pulvinar placerat leo vitae pellentesque. Nulla vel elit quis eros aliquet gravida. Sed nec laoreet lacus. Etiam ultrices risus vel pulvinar suscipit. Praesent fermentum risus nec tortor imperdiet, vitae aliquet tellus feugiat. Curabitur nec arcu et dui maximus egestas ac vitae dolor. Cras nec arcu mattis, convallis enim vitae, tempor mi. Vivamus consequat id est eget dictum. Aliquam finibus, tellus eu laoreet dignissim, neque enim sodales quam, sagittis venenatis mi nisl vitae ligula. Donec auctor dui nulla, eget aliquet risus libero"
    })
    void should_create_new_task_with_description_and_title_inside_limits(String title, String description) {
        Task task = new Task(title, description);
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(TaskStatus.InProgress, task.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus quis tellus in turpis dapibus nulla."
    })
    void shoul_not_be_able_to_create_task_with_title_outside_limits(String title) {
        try {
            new Task(title, "Descrição");
        } catch (Exception e) {
            assertEquals("Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres",
                    e.getMessage());
        }
    }

    @Test
    void should_not_be_able_to_create_task_with_description_outside_limits() {
        try {
            new Task("Título",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus quis tellus in turpis dapibus nulla.");
        } catch (Exception e) {
            assertEquals("Descrição da tarefa deve possuir no máximo 5000 caracteres", e.getMessage());
        }
    }

    @Test
    void should_create_new_task_with_priority_high() {
        Task task = new Task("Título", "Descrição", TaskPriority.High);
        assertEquals(TaskPriority.High, task.getPriority());
    }

    @Test
    void should_create_new_task_with_type_and_due_days() {
        Task task = new Task("Título", "Descrição", TaskType.Free, 5, TaskPriority.High);
        assertEquals(TaskType.Free, task.getType());
        assertEquals(5, task.getDueDays());
    }

    @Test
    void should_return_task_to_string() {
        Task task = new Task("Título", "Descrição", TaskType.Free, 5, TaskPriority.High);
        assertEquals(
                "Task [id=null, title=Título, description=Descrição, status=InProgress, type=Free, dueDate=null, dueDays=5, priority=High]",
                task.toString());
    }
}