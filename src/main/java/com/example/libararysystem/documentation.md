 # Säkerhetsarkitektur och documentation
### 1.JWT vs sessions

**Nördelar med JWT:**
- ingen server side session behövs(stateless artiketur)
- passar bra för microservices 
- token inhåller claims och kan validera snabb

**Nackdelar**
- Token kan bli stora 
- Secert key måsta lagras i sakert plats annars tokens är osäkra

**När används sessions?**
- Prioriterar enkelhet.
- Sessions är inte så många.


### 2.Threat Model 
**Hot - skydd**
- Obehörig åtkomst - roller baserat access , metod nivå säkerhet
- XSS , SQL injections -  Spring Data JPA , input validation
- Brute force login - account lockout(logginAttemptService), BCrypt.
- token manipulation - HTTPS , short-lived tokens. 
- password leaks - BCrypt.




### 3.Säkerhetsarkitekturen ochDesignbesult
* rol-baserad auktorisering 
* stateless autentisering 
* spring security 
* JWT över sessions
* secret key i servern
* extra headers (HSTP , CSP , XSS)

### 4.kvarvarande risker och begränsingar
- Admin kan radera en annan admin : 

      @PreAuthorize("hasRole('ADMIN')")
      public Optional<User> deleteUserById(Long id) {
      Optional<User> user = userRepo.findById(id);
      if(user.isPresent()) {
      userRepo.delete(user.get());
      }
    return user;
    }
- CSRF inaktiverat:

      .xssProtection(xxs-> xxs.disable())

- XSS skydd inaktiverad:

      .xssProtection(xxs-> xxs.disable())
- ingen multifaktorautentisering


### 5.Reflektion 
- säkerhetsheaders (HSTS m CSP , XSS) sykddar men kan ibland t.ex blockera inline-skript.
- JWT gör systemet snabbt och ingen extra sessionhantering för användaren, 
men det innbär att det blir svårare att revoke en användares åtkomst.
- kort livstid token ger högre säkerhet men anvädaren måste logga in oftare (sämre UX).
- lång livstid get bättre ux men högre risk om en token stjäls.
- jag har balans JWT med rimlig livstid plus rollbaserad accesscontroll så båda säkert och användvänligt





