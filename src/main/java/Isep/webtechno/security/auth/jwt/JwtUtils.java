package Isep.webtechno.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public class JwtUtils {

    public static final String headerAuthorization = "Authorization";
    public static final String headerAuthorizationPrefix = "Bearer ";
    public static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.ES384;
    public static final String secretKey = "LAMARCHEDESVERTUEUXESTSEMEEDOBSTACLESQUISONTLESENTREPRISESEGOISTESQUEFAITSANSFINSURGIRLOEUVREDUMALINBENISOITILLHOMMEDEBONNEVOLONTEQUIAUNOMDELACHARITESEFAITLEBERGERDESFAIBLESQUILGUIDEDANSLAVALLEEDOMBREDELAMORTETDESLARMESCARILESTLEGARDIENDESONFREREETLAPROVIDENCEDESENFANTSEGARESJABATTRAIALORSLEBRASDUNETERRIBLECOLEREDUNEVENGEANCEFURIEUSEETEFFRAYANTESURLESHORDESIMPIESQUIPOURCHASSENTETREDUISENTANEANTLESBREBISDEDIEUETTUCONNAITRASPOURQUOIMONNOMESTLETERNELQUANDSURTOISABATTRALAVENGEANCEDUTOUTPUISSANT";
//    public static final String secretKey = "";


    public static Key getKey() {
        return new Key() {
            @Override
            public String getAlgorithm() {
                return signatureAlgorithm.name();
            }

            @Override
            public String getFormat() {
                return null;
            }

            @Override
            public byte[] getEncoded() {
                return secretKey.getBytes(StandardCharsets.UTF_8);
            }
        };
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

}
