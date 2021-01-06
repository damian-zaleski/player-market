package pl.degath.banking;

import pl.degath.banking.port.ExternalTeamApi;

import java.util.UUID;

public class FakeExternalTeamService implements ExternalTeamApi {

    @Override
    public boolean existsBy(UUID id) {
        return id.equals(UUID.fromString("8bedaa6d-558f-40da-95cb-c8c4f03be310")) ||
                id.equals(UUID.fromString("0a898bc4-d64d-4151-9182-c9f10882718f"));
    }
}
