import torch


####################
# Utility functions
####################
def save_model(model, filename):
    print('Best model so far, saving it...')
    torch.save(model.state_dict(), filename)


def report_loss(epoch, D_loss, G_loss, recon_loss, z_loss):
    # changed data[0] -> item(). working! no warning
    print('Epoch-{}; D_loss: {:.4}; G_loss: {:.4}; recon_loss: {:.4} z_loss: {:.4}'.format(epoch, D_loss.item(),
                                                                                           G_loss.item(),
                                                                                           recon_loss.item(),
                                                                                           0.0 if z_loss is None else
                                                                                           z_loss.item()))
    # print('Epoch-{}; D_loss: {:.4}; G_loss: {:.4}; recon_loss: {:.4} z_loss: {:.4}'.format(epoch, D_loss.data[0],
    #                                                                                        G_loss.data[0],
    #                                                                                        recon_loss.data[0],
    #                                                                                        0.0 if z_loss is None else
    #                                                                                        z_loss.data[0]))
