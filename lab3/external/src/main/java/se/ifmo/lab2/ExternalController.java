package se.ifmo.lab2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/requests")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExternalController {

  @PostMapping
  public Status responseToRhequest() throws InterruptedException {
    Thread.sleep(3000L);
    return new Status("OK");
  }

  @Data
  @AllArgsConstructor
  public class Status {
    private String status;
  }
}
