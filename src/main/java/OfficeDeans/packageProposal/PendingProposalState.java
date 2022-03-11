package OfficeDeans.packageProposal;

public class PendingProposalState implements ProposalState {
    @Override
    public void updateState(Proposal proposal) {
        proposal.setDecisionState("Oczekujace");
    }
}
