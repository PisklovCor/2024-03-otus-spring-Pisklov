package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Book;

import static org.springframework.security.acls.domain.BasePermission.READ;

@RequiredArgsConstructor
@Service
public class PostProcessInstallingPermission {

    private static final String GRANTED_AUTHORITY_SID = "ROLE_EDITOR";

    private static final String PRINCIPAL_SID = "user";

    private final MutableAclService mutableAclService;

    /**
     * Псосле сохраненяи новой книги, устанавливаем права, в этой же транзакции, на нее
     * для этого же пользователя
     *
     * @param createdBook сохраненная книга
     */
    public void settingRights(Book createdBook) {

        ObjectIdentity oid = new ObjectIdentityImpl(createdBook);
        var acl = mutableAclService.createAcl(oid);

        acl.insertAce(acl.getEntries().size(), READ,
                new GrantedAuthoritySid(GRANTED_AUTHORITY_SID), true);
        acl.insertAce(acl.getEntries().size(), READ,
                new PrincipalSid(PRINCIPAL_SID), true);

        mutableAclService.updateAcl(acl);
    }
}
