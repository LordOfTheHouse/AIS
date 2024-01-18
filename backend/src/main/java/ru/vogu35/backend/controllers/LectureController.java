package ru.vogu35.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.UserInfo;
import ru.vogu35.backend.models.UserResponse;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.repositories.GroupRepository;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final KeycloakApiProxy keycloakApiProxy;
    private final GroupRepository groupRepository;

    public LectureController(KeycloakApiProxy keycloakApiProxy, GroupRepository groupRepository) {
        this.keycloakApiProxy = keycloakApiProxy;
        this.groupRepository = groupRepository;
    }

    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupModel>> getGroups(){

        return ResponseEntity.ok(null);
    }
    @GetMapping("/{group}")
    public ResponseEntity<List<UserResponse>> getStudentGroup(@PathVariable("group") String groupName){
        log.info("students group: {}", groupName);
        return ResponseEntity.ok(keycloakApiProxy.findUserByGroup(groupName));
    }

}
