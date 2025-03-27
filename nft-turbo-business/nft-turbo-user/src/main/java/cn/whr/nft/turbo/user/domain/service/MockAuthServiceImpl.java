package cn.whr.nft.turbo.user.domain.service;

/**
 * Mock的认证服务
 *
 * @author whr
 */
public class MockAuthServiceImpl implements AuthService {
    @Override
    public boolean checkAuth(String realName, String idCard) {
        return true;
    }
}
