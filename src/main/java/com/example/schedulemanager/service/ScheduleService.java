package com.example.schedulemanager.service;

import com.example.schedulemanager.dto.*;
import com.example.schedulemanager.entity.Schedule;
import com.example.schedulemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getName(),
                request.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getName(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findByName(String name) {
        List<ScheduleResponse> dtos = new ArrayList<>();

        if (name == null || name.isBlank()) {
            List<Schedule> schedules = scheduleRepository.findAll();
            for (Schedule schedule : schedules) {
                ScheduleResponse dto = new ScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getName(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                dtos.add(dto);
            }
            return dtos;
        }

        List<Schedule> schedules = scheduleRepository.findByName(name);
        for (Schedule schedule : schedules) {
            ScheduleResponse dto = new ScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getName(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;

    }


    @Transactional
    public ScheduleResponse findOne(Long scheduleId) {

        Schedule schedule = getOrThrow(scheduleId);

        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public ScheduleResponse update(UpdateScheduleRequest request, Long scheduleId) {

        Schedule schedule = getOrThrow(scheduleId);

        if (!request.getPassword().equals(schedule.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 틀립니다."
            );
        }

        schedule.update(request.getTitle(), request.getName());

        Schedule updatedSchedule =  scheduleRepository.save(schedule);

        return new ScheduleResponse(
                updatedSchedule.getId(),
                updatedSchedule.getTitle(),
                updatedSchedule.getContent(),
                updatedSchedule.getName(),
                updatedSchedule.getCreatedAt(),
                updatedSchedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(DeleteScheduleRequest request, Long scheduleId) {

        Schedule schedule = getOrThrow(scheduleId);

        if (!request.getPassword().equals(schedule.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 틀립니다."
            );
        }

        scheduleRepository.deleteById(scheduleId);
    }

    private Schedule getOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "없는 일정입니다."
                )
        );
    }
}
