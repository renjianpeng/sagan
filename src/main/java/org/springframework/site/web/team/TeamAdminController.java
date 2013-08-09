package org.springframework.site.web.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.site.domain.team.MemberProfile;
import org.springframework.site.domain.team.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
public class TeamAdminController {

	private final TeamService teamService;

	@Autowired
	public TeamAdminController(TeamService teamService) {
		this.teamService = teamService;
	}

	@RequestMapping(value = "/admin/profile", method = {GET, HEAD})
	public String editProfileForm(Principal principal, Model model) {
		MemberProfile profile = teamService.fetchMemberProfile(new Long(principal.getName()));
		model.addAttribute("profile", profile);
		model.addAttribute("formAction", "/admin/profile");
		return "admin/team/edit";
	}

	@RequestMapping(value = "/admin/team/{username}", method = {GET, HEAD})
	public String editTeamMemberForm(@PathVariable("username") String username, Model model) {
		MemberProfile profile = teamService.fetchMemberProfileUsername(username);
		model.addAttribute("profile", profile);
		model.addAttribute("formAction", "/admin/team/" + username);
		return "admin/team/edit";
	}


	@RequestMapping(value = "/admin/profile", method = PUT)
	public String saveProfile(Principal principal, MemberProfile profile) {
		teamService.updateMemberProfile(new Long(principal.getName()), profile);
		return "redirect:/admin/profile";
	}

	@RequestMapping(value = "/admin/team/{username}", method = PUT)
	public String saveTeamMember(@PathVariable("username") String username, MemberProfile profile) {
		teamService.updateMemberProfile(username, profile);
		return "redirect:/admin/team/" + username;
	}

}
