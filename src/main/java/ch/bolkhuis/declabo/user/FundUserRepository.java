package ch.bolkhuis.declabo.user;

public interface FundUserRepository extends BaseUserRepository<FundUser> {
    FundUser findByFundId(long fundId);
}
