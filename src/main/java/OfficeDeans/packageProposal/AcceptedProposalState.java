package OfficeDeans.packageProposal;

public class AcceptedProposalState implements ProposalState {
    @Override
    public void updateState(Proposal proposal) {
        proposal.setDecisionState("Przyjete");
    }
}